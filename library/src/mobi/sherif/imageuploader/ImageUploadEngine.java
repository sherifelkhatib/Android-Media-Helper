package mobi.sherif.imageuploader;

import java.io.File;
import java.text.SimpleDateFormat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.SparseArray;

/**
 * @author Sherif elKhatib - shush
 *
 */
public class ImageUploadEngine {
	private static final int LOADPICTURE = 112;
	private static final int TAKEPICTURE = 113;
	private static final String STATE_COUNT = ImageUploadEngine.class.getName() + "count";
	private static final String STATE_ID = ImageUploadEngine.class.getName() + "mIdentifier";
	private static final String STATE_PHOTOPATH = ImageUploadEngine.class.getName() + "mCurrentPhotoPath";
	private static final String STATE_PENDING = ImageUploadEngine.class.getName() + "mPending";

	private static SparseArray<ImageUploadEngine> mInstances = new SparseArray<ImageUploadEngine>();
	private static int count = 0;

	/**
	 * @author Sherif elKhatib - shush
	 * 
	 * Interface defining the callbacks received because of an Image Choose/Take request.
	 *
	 */
	public interface ImageChooseCallback {
		void onChosen(ImageUploadEngine engine, String path, boolean newPicture);
		void onCanceled(ImageUploadEngine engine);
		void onError(ImageUploadEngine engine, Exception ex);
	}

	public static class Builder {
		private LoadingListener loadinglistener;
		private ImageChooseCallback callback;
		private Bundle state;
		private ActivityManager manager;
		private ImageFileCreator filecreator;
		public Builder(android.app.Activity activity, Bundle state) {
			this.manager = ActivityManager.StaticBuilder.build(activity);
			this.state = state;
		}
		public Builder(android.support.v4.app.FragmentActivity activity, Bundle state) {
			this.manager = ActivityManager.StaticBuilder.build(activity);
			this.state = state;
		}
		public Builder(android.app.Fragment fragment, Bundle state) {
			this.manager = ActivityManager.StaticBuilder.build(fragment);
			this.state = state;
		}
		public Builder(android.support.v4.app.Fragment fragment, Bundle state) {
			this.manager = ActivityManager.StaticBuilder.build(fragment);
			this.state = state;
		}
		/**
		 * @author Sherif elKhatib - shush
		 * Optional function to set the callback. However, note that if this is not called,
		 * the callback will be the activity or fragment passed in the constructor of this {@link Builder} instance.
		 * @param callback The {@link ImageChooseCallback} instance that will receive callbacks
		 * @see ImageChooseCallback
		 */
		public Builder setCallback(ImageChooseCallback callback) {
			this.callback = callback;
			return this;
		}
		/**
		 * @author Sherif elKhatib - shush
		 * Optional function to set the {@link ImageFileCreator} instance. If this is not called,
		 * a default implementation will be used that saves images in folder named "Uploads" in the {@link Environment#DIRECTORY_PICTURES} folder.
		 * The names of the files will be "Upload_yyyyMMdd_HHmmss.jpg" where `yyyyMMdd_HHmmss` is replaced with the date using {@link SimpleDateFormat}.
		 * @param filecreator The {@link ImageFileCreator} instance that will be used to create new files when needed
		 * @see ImageFileCreator 
		 */
		public Builder setFileCreator(ImageFileCreator filecreator) {
			this.filecreator = filecreator;
			return this;
		}
		/**
		 * @author Sherif elKhatib - shush
		 * Optional function to set a Loading Listener. The {@link LoadingListener} instance will receive callbacks
		 * for loading requests. If the engine needs to write to a file or read from a file, this will be notified.
		 * @param callback The {@link LoadingListener} instance that will receive callbacks
		 * @see setLoadingListener
		 */
		public Builder setLoadingListener(LoadingListener listener) {
			this.loadinglistener = listener;
			return this;
		}
		public ImageUploadEngine build() {
			if(callback == null) {
				callback = manager.getCallback();
			}
			return new ImageUploadEngine(state, manager, callback, filecreator, loadinglistener);
		}
	}

	private int mIdentifier;
	private String mCurrentPhotoPath;
	private boolean mPending;
	private ActivityManager mActivityManager;
	private ImageFileCreator mFileCreator;
	private ImageChooseCallback mCallback;
	private LoadingListener mLoadListener;

	private ImageUploadEngine(Bundle state, ActivityManager manager, ImageChooseCallback callback, ImageFileCreator filecreator) {
		this(state, manager, callback, filecreator, null);
	}
	private ImageUploadEngine(Bundle state, ActivityManager manager, ImageChooseCallback callback, ImageFileCreator filecreator, LoadingListener listener) {
		mActivityManager = manager;
		mCallback = callback;
		mFileCreator = filecreator;
		if(mFileCreator == null) {
			mFileCreator = new DefaultImageFileCreator();
		}
		if(state != null) {
			int thecount = state.getInt(STATE_COUNT, 0);
			if(thecount > count) {
				count = thecount;
			}

			mIdentifier = state.getInt(STATE_ID, 0);
			mCurrentPhotoPath = state.getString(STATE_PHOTOPATH, null);
			mPending = state.getBoolean(STATE_PENDING, false);
		}
		else {
			mIdentifier = ++count;
			mCurrentPhotoPath = null;
			mPending = false;
		}
		mLoadListener = listener;
		mInstances.put(mIdentifier, this);
	}
	protected static ImageUploadEngine get(int identifier) {
		return mInstances.get(identifier);
	}

	/**
	 * @author Sherif elKhatib
	 * 
	 * Use this to ask the user for a new image.
	 * @param title
	 * @param buttonNewphoto
	 * @param buttonExistingphoto
	 * @param message
	 * @param icon
	 */
	public void performImageAsk(int title, int buttonNewphoto, int buttonExistingphoto, int message, int icon) {
		FragmentYesNoDialogBuilder b = new FragmentYesNoDialogBuilder(mActivityManager.getContext(), mIdentifier)
		.setTitle(title)
		.setYes(buttonNewphoto)
		.setNo(buttonExistingphoto)
		.setMessage(message)
		.setIcon(icon);
		mActivityManager.show(b, ImageUploadEngine.class.getName() + mIdentifier);
	}
	public void performCancel() {
		mCallback.onCanceled(this);
	}
	public void performImageTake() {
		try {
			Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File f = mFileCreator.createImageFile();
			mCurrentPhotoPath = f.getAbsolutePath();
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
			mPending = true;
			mActivityManager.startActivityForResult(takePictureIntent, TAKEPICTURE);
		} catch (ImageFileCreationException e) {
			mCallback.onError(this, e);
		} catch (Exception e) {
			mCallback.onError(this, e);
		}
	}
	public void performImageChoose() {
		try {
			Intent mediaChooser = new Intent(Intent.ACTION_GET_CONTENT);
			mediaChooser.setType("image/*");
			mPending = true;
			mActivityManager.startActivityForResult(mediaChooser, LOADPICTURE);
		} catch (Exception e) {
			mCallback.onError(this, e);
		}
	}

	public void onSaveInstanceState(Bundle outState) {
		outState.putString(STATE_PHOTOPATH, mCurrentPhotoPath);
		outState.putInt(STATE_COUNT, count);
		outState.putInt(STATE_ID, mIdentifier);
		outState.putBoolean(STATE_PENDING, mPending);
	}
	public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
		if(!mPending) return false;
		switch(requestCode) {
		case LOADPICTURE:
			switch(resultCode) {
			case android.app.Activity.RESULT_OK:
				
		        Uri imageUri = data.getData();
		        new ImageLoadTask(mActivityManager.getContext(), mFileCreator) {
		        	protected void onPreExecute() {
		        		if(mLoadListener != null)
		        			mLoadListener.onLoadingStarted();
		        	};
		        	@Override
		        	protected void onPostExecute(String result) {
		        		super.onPostExecute(result);
		        		if(mLoadListener != null)
		        			mLoadListener.onLoadingDone();
		        		if(result != null) {
		        			mCurrentPhotoPath = result;
		        		}
						if(mCurrentPhotoPath != null)
							mCallback.onChosen(ImageUploadEngine.this, mCurrentPhotoPath, false);
						else
							mCallback.onError(ImageUploadEngine.this, mException!=null?mException:new Exception("Unable to extract selected image."));
		        	}
		        }.execute(imageUri);
				break;
			case android.app.Activity.RESULT_CANCELED:
				mCallback.onCanceled(this);
				break;
			}
			return true;
		case TAKEPICTURE:
			switch(resultCode) {
			case android.app.Activity.RESULT_OK:
				mCallback.onChosen(this, mCurrentPhotoPath, true);
				break;
			case android.app.Activity.RESULT_CANCELED:
				mCallback.onCanceled(this);
				break;
			}
			return true;
		default:
			return false;
		}
	}
}
