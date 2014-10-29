package mobi.sherif.imageuploader;

import java.io.File;
import java.text.SimpleDateFormat;

import mobi.sherif.imageuploader.MediaEngine.FileCreator.FileCreationException;
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
public class MediaEngine {
	/**
	 * @author Sherif elKhatib - shush
	 * 
	 *         Builder utility class for Media Engine.
	 * 
	 */
	public static class Builder {
		private LoadingListener loadinglistener;
		private MediaChooseCallback callback;
		private Bundle state;
		private ActivityManager manager;
		private FileCreator filecreator;

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
		 * @author Sherif elKhatib - shush Optional function to set the
		 *         callback. However, note that if this is not called, the
		 *         callback will be the activity or fragment passed in the
		 *         constructor of this {@link Builder} instance.
		 * @param callback
		 *            The {@link MediaChooseCallback} instance that will receive
		 *            callbacks
		 * @see MediaChooseCallback
		 */
		public Builder setCallback(MediaChooseCallback callback) {
			this.callback = callback;
			return this;
		}

		/**
		 * @author Sherif elKhatib - shush Optional function to set the
		 *         {@link FileCreator} instance. If this is not called, a
		 *         default implementation will be used that saves images in
		 *         folder named "Uploads" in the
		 *         {@link Environment#DIRECTORY_PICTURES} folder. The names of
		 *         the files will be "Upload_yyyyMMdd_HHmmss.jpg" where
		 *         `yyyyMMdd_HHmmss` is replaced with the date using
		 *         {@link SimpleDateFormat}.
		 * @param filecreator
		 *            The {@link FileCreator} instance that will be used to
		 *            create new files when needed
		 * @see FileCreator
		 */
		public Builder setFileCreator(FileCreator filecreator) {
			this.filecreator = filecreator;
			return this;
		}

		/**
		 * @author Sherif elKhatib - shush Optional function to set a Loading
		 *         Listener. The {@link LoadingListener} instance will receive
		 *         callbacks for loading requests. If the engine needs to write
		 *         to a file or read from a file, this will be notified.
		 * @param callback
		 *            The {@link LoadingListener} instance that will receive
		 *            callbacks
		 * @see setLoadingListener
		 */
		public Builder setLoadingListener(LoadingListener listener) {
			this.loadinglistener = listener;
			return this;
		}

		public MediaEngine build( ) {
			if (callback == null) {
				callback = manager.getCallback();
			}
			return new MediaEngine(state, manager, callback, filecreator, loadinglistener);
		}
	}

	public static class Result {
		MediaEngine engine;
		boolean canceled;
		boolean error;
		boolean newfile;
		int type;
		String path;
		Exception exception;

		public MediaEngine getEngine( ) {
			return engine;
		}

		public boolean isCanceled( ) {
			return canceled;
		}

		public boolean isError( ) {
			return error;
		}

		public boolean isNewfile( ) {
			return newfile;
		}

		public int getType( ) {
			return type;
		}

		public String getPath( ) {
			return path;
		}

		public Exception getException( ) {
			return exception;
		}
	}

	/**
	 * @author Sherif elKhatib - shush
	 * 
	 *         Interface defining the callbacks received because of an Image
	 *         Choose/Take request.
	 * 
	 */
	public interface MediaChooseCallback {
		void onResult(MediaEngine engine, Result result);
	}

	public interface LoadingListener {
		public void onLoadingStarted( );

		public void onLoadingDone( );
	}

	public interface FileCreator {
		File createFile(String ext) throws FileCreationException;

		public class FileCreationException extends Exception {
			/**
			 * 
			 */
			private static final long serialVersionUID = 153850828158406491L;

			public FileCreationException( ) {
				super();
			}

			public FileCreationException(Exception original) {
				super();
			}

			Exception mException;

			public Exception getOriginalException( ) {
				return mException;
			}
		}
	}

	/**
	 * @author Sherif elKhatib
	 * 
	 *         Use this to ask the user for a image (new or from gallery).
	 * @param title
	 * @param buttonNewphoto
	 * @param buttonExistingphoto
	 * @param message
	 * @param icon
	 */
	public void performImageAsk(int title, int buttonNewphoto, int buttonExistingphoto, int message, int icon) {
		mType = TYPE_IMAGE;
		performMediaAsk(title, buttonNewphoto, buttonExistingphoto, message, icon, mType);
	}

	/**
	 * @author Sherif elKhatib
	 * 
	 *         Use this to ask the user for a new video (new or from gallery).
	 * @param title
	 * @param buttonNewvideo
	 * @param buttonExistingvideo
	 * @param message
	 * @param icon
	 */
	public void performVideoAsk(int title, int buttonNewvideo, int buttonExistingvideo, int message, int icon) {
		mType = TYPE_VIDEO;
		performMediaAsk(title, buttonNewvideo, buttonExistingvideo, message, icon, mType);
	}

	/**
	 * @author Sherif elKhatib
	 * 
	 *         Take a new picture.
	 */
	public void performImageTake( ) {
		performMediaTake(TYPE_IMAGE);
	}

	/**
	 * @author Sherif elKhatib
	 * 
	 *         Capture a new video.
	 */
	public void performVideoTake( ) {
		performMediaTake(TYPE_VIDEO);
	}

	/**
	 * @author Sherif elKhatib
	 * 
	 *         Select a picture from gallery.
	 */
	public void performImageChoose( ) {
		performMediaChoose(TYPE_IMAGE);
	}

	/**
	 * @author Sherif elKhatib
	 * 
	 *         Select a video from gallery
	 */
	public void performVideoChoose( ) {
		performMediaChoose(TYPE_VIDEO);
	}

	public void performCancel( ) {
		Result r = new Result();
		r.engine = this;
		r.error = true;
		r.canceled = true;
		r.path = null;
		r.type = mType;
		mCallback.onResult(MediaEngine.this, r);
	}

	public void onSaveInstanceState(Bundle outState) {
		outState.putString(STATE_PHOTOPATH, mCurrentPhotoPath);
		outState.putInt(STATE_COUNT, count);
		outState.putInt(STATE_ID, mIdentifier);
		outState.putBoolean(STATE_PENDING, mPending);
	}

	public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
		if ( !mPending) return false;
		if (resultCode == android.app.Activity.RESULT_CANCELED) {
			Result r = new Result();
			r.engine = this;
			r.error = true;
			r.canceled = true;
			r.path = null;
			mCallback.onResult(MediaEngine.this, r);
		}
		switch (requestCode) {
		case LOADPICTURE:
			switch (resultCode) {
			case android.app.Activity.RESULT_OK:
				Uri imageUri = data.getData();
				new FileLoadTask(mActivityManager.getContext(), mFileCreator == null ? new DefaultImageFileCreator() : mFileCreator) {
					protected void onPreExecute( ) {
						if (mLoadListener != null) mLoadListener.onLoadingStarted();
					};

					@Override
					protected void onPostExecute(String result) {
						super.onPostExecute(result);
						if (mLoadListener != null) mLoadListener.onLoadingDone();
						if (result != null) {
							mCurrentPhotoPath = result;
						}
						if (mCurrentPhotoPath != null) {
							Result r = new Result();
							r.engine = MediaEngine.this;
							r.error = false;
							r.canceled = false;
							r.path = mCurrentPhotoPath;
							r.exception = null;
							r.newfile = false;
							r.type = TYPE_IMAGE;
							mCallback.onResult(MediaEngine.this, r);
						} else {
							Result r = new Result();
							r.engine = MediaEngine.this;
							r.error = true;
							r.canceled = false;
							r.path = null;
							r.exception = mException != null ? mException : new Exception("Unable to extract selected image.");
							r.type = TYPE_IMAGE;
							mCallback.onResult(MediaEngine.this, r);
						}
					}
				}.execute(imageUri);
				break;
			}
			return true;
		case TAKEPICTURE:
			switch (resultCode) {
			case android.app.Activity.RESULT_OK:
				Result r = new Result();
				r.engine = MediaEngine.this;
				r.error = false;
				r.canceled = false;
				r.path = mCurrentPhotoPath;
				r.exception = null;
				r.type = TYPE_IMAGE;
				r.newfile = true;
				mCallback.onResult(MediaEngine.this, r);
				break;
			}
			return true;
		case LOADVIDEO:
			switch (resultCode) {
			case android.app.Activity.RESULT_OK:
				Uri imageUri = data.getData();
				new FileLoadTask(mActivityManager.getContext(), mFileCreator == null ? new DefaultVideoFileCreator() : mFileCreator) {
					protected void onPreExecute( ) {
						if (mLoadListener != null) mLoadListener.onLoadingStarted();
					};

					@Override
					protected void onPostExecute(String result) {
						super.onPostExecute(result);
						if (mLoadListener != null) mLoadListener.onLoadingDone();
						if (result != null) {
							mCurrentPhotoPath = result;
						}
						if (mCurrentPhotoPath != null) {
							Result r = new Result();
							r.engine = MediaEngine.this;
							r.error = false;
							r.canceled = false;
							r.path = mCurrentPhotoPath;
							r.exception = null;
							r.newfile = false;
							r.type = TYPE_VIDEO;
							mCallback.onResult(MediaEngine.this, r);
						} else {
							Result r = new Result();
							r.engine = MediaEngine.this;
							r.error = true;
							r.canceled = false;
							r.path = null;
							r.exception = mException != null ? mException : new Exception("Unable to extract selected image.");
							r.type = TYPE_VIDEO;
							mCallback.onResult(MediaEngine.this, r);
						}
					}
				}.execute(imageUri);
				break;
			}
			return true;
		case TAKEVIDEO:
			switch (resultCode) {
			case android.app.Activity.RESULT_OK:
				Result r = new Result();
				r.engine = MediaEngine.this;
				r.error = false;
				r.canceled = false;
				r.path = mCurrentPhotoPath;
				r.exception = null;
				r.type = TYPE_VIDEO;
				r.newfile = true;
				mCallback.onResult(MediaEngine.this, r);
				break;
			}
			return true;
		default:
			return false;
		}
	}

	protected static MediaEngine get(int identifier) {
		return mInstances.get(identifier);
	}

	private static final int LOADPICTURE = 112;
	private static final int TAKEPICTURE = 113;
	private static final int LOADVIDEO = 114;
	private static final int TAKEVIDEO = 115;
	private static final String STATE_COUNT = MediaEngine.class.getName() + "count";
	private static final String STATE_ID = MediaEngine.class.getName() + "mIdentifier";
	private static final String STATE_PHOTOPATH = MediaEngine.class.getName() + "mCurrentPhotoPath";
	private static final String STATE_PENDING = MediaEngine.class.getName() + "mPending";
	private static SparseArray<MediaEngine> mInstances = new SparseArray<MediaEngine>();
	private static int count = 0;
	private static int TYPE_IMAGE = 1;
	private static int TYPE_VIDEO = 2;
	private int mIdentifier;
	private String mCurrentPhotoPath;
	private int mType;
	private boolean mPending;
	private ActivityManager mActivityManager;
	private FileCreator mFileCreator;
	private MediaChooseCallback mCallback;
	private LoadingListener mLoadListener;

	private MediaEngine(Bundle state, ActivityManager manager, MediaChooseCallback callback, FileCreator filecreator) {
		this(state, manager, callback, filecreator, null);
	}

	private MediaEngine(Bundle state, ActivityManager manager, MediaChooseCallback callback, FileCreator filecreator, LoadingListener listener) {
		mActivityManager = manager;
		mCallback = callback;
		mFileCreator = filecreator;
		if (state != null) {
			int thecount = state.getInt(STATE_COUNT, 0);
			if (thecount > count) {
				count = thecount;
			}
			mIdentifier = state.getInt(STATE_ID, 0);
			mCurrentPhotoPath = state.containsKey(STATE_PHOTOPATH) ? state.getString(STATE_PHOTOPATH) : null;
			mPending = state.getBoolean(STATE_PENDING, false);
		} else {
			mIdentifier = ++count;
			mCurrentPhotoPath = null;
			mPending = false;
		}
		mLoadListener = listener;
		mInstances.put(mIdentifier, this);
	}

	private void performMediaAsk(int title, int buttonNew, int buttonExisting, int message, int icon, int type) {
		mType = type;
		FragmentYesNoDialogBuilder b = new FragmentYesNoDialogBuilder(mActivityManager.getContext(), mIdentifier).setTitle(title).setYes(buttonNew).setNo(buttonExisting).setMessage(message).setIcon(icon);
		mActivityManager.show(b, MediaEngine.class.getName() + mIdentifier);
	}

	private void performMediaTake(int type) {
		mType = type;
		performMediaTake();
	}

	protected void performMediaTake( ) {
		if (mType == TYPE_IMAGE) {
			try {
				Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				File f = mFileCreator == null ? ( new DefaultImageFileCreator() ).createFile(null) : mFileCreator.createFile(null);
				mCurrentPhotoPath = f.getAbsolutePath();
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
				mPending = true;
				mActivityManager.startActivityForResult(takePictureIntent, TAKEPICTURE);
			} catch (FileCreationException e) {
				Result r = new Result();
				r.engine = this;
				r.error = true;
				r.canceled = false;
				r.path = null;
				r.type = mType;
				r.exception = e;
				mCallback.onResult(MediaEngine.this, r);
			} catch (Exception e) {
				Result r = new Result();
				r.engine = this;
				r.error = true;
				r.canceled = false;
				r.path = null;
				r.type = mType;
				r.exception = e;
				mCallback.onResult(MediaEngine.this, r);
			}
		} else if (mType == TYPE_VIDEO) {
			try {
				Intent takePictureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
				takePictureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
				File f = mFileCreator == null ? ( new DefaultVideoFileCreator() ).createFile(null) : mFileCreator.createFile(null);
				mCurrentPhotoPath = f.getAbsolutePath();
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
				mPending = true;
				mActivityManager.startActivityForResult(takePictureIntent, TAKEVIDEO);
			} catch (FileCreationException e) {
				Result r = new Result();
				r.engine = this;
				r.error = true;
				r.canceled = false;
				r.path = null;
				r.exception = e;
				r.type = mType;
				mCallback.onResult(MediaEngine.this, r);
			} catch (Exception e) {
				Result r = new Result();
				r.engine = this;
				r.error = true;
				r.canceled = false;
				r.path = null;
				r.exception = e;
				r.type = mType;
				mCallback.onResult(MediaEngine.this, r);
			}
		}
	}

	private void performMediaChoose(int type) {
		mType = type;
		performMediaChoose();
	}

	protected void performMediaChoose( ) {
		if (mType == TYPE_IMAGE) {
			try {
				Intent mediaChooser = new Intent(Intent.ACTION_GET_CONTENT);
				mediaChooser.setType("image/*");
				mPending = true;
				mActivityManager.startActivityForResult(mediaChooser, LOADPICTURE);
			} catch (Exception e) {
				Result r = new Result();
				r.engine = this;
				r.error = true;
				r.canceled = false;
				r.path = null;
				r.type = mType;
				r.exception = e;
				mCallback.onResult(MediaEngine.this, r);
			}
		} else if (mType == TYPE_VIDEO) {
			try {
				Intent mediaChooser = new Intent(Intent.ACTION_GET_CONTENT);
				mediaChooser.setType("video/*");
				mPending = true;
				mActivityManager.startActivityForResult(mediaChooser, LOADVIDEO);
			} catch (Exception e) {
				Result r = new Result();
				r.engine = this;
				r.error = true;
				r.type = mType;
				r.canceled = false;
				r.path = null;
				r.exception = e;
				mCallback.onResult(MediaEngine.this, r);
			}
		}
	}
}
