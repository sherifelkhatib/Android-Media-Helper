package mobi.sherif.imageuploader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

class ImageLoadTask extends AsyncTask<Uri, Void, String> {
	Context mContext;
	ImageFileCreator mFileCreator;
	Exception mException;
	public ImageLoadTask(Context cxt, ImageFileCreator filecreator) {
		mContext = cxt;
		mFileCreator = filecreator;
	}
	@Override
	protected String doInBackground(Uri... params) {
		if(params == null || params.length == 0) return null;
		Uri uri = params[0];
		File f = null;
		try {
			f = mFileCreator.createImageFile();
			InputStream is = mContext.getContentResolver().openInputStream(uri);
			OutputStream os = new FileOutputStream(f);
			byte[] buffer = new byte[1024];
			int len;
			while ((len = is.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
			os.close();
			is.close();
			return f.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
			mException = e;
			if(f != null) {
				try {
					f.delete();
				} catch (Exception e2) {
				}
			}
		}
		return null;
	}

}
