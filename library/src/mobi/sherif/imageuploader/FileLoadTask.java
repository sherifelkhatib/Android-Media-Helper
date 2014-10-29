package mobi.sherif.imageuploader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import mobi.sherif.imageuploader.MediaEngine.FileCreator;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.webkit.MimeTypeMap;

class FileLoadTask extends AsyncTask<Uri, Void, String> {
	Context mContext;
	FileCreator mFileCreator;
	Exception mException;

	public FileLoadTask(Context cxt, FileCreator filecreator) {
		mContext = cxt;
		mFileCreator = filecreator;
	}

	@Override
	protected String doInBackground(Uri... params) {
		if (params == null || params.length == 0) return null;
		Uri uri = params[0];
		File f = null;
		try {
			ContentResolver cR = mContext.getContentResolver();
			MimeTypeMap mime = MimeTypeMap.getSingleton();
			// String type = mime.getExtensionFromMimeType(cR.getType(uri));
			if (cR.getType(uri) != null && mime.getExtensionFromMimeType(cR.getType(uri)) != null) {
				f = mFileCreator.createFile(mime.getExtensionFromMimeType(cR.getType(uri)));
			} else {
				f = mFileCreator.createFile(null);
			}
			InputStream is = mContext.getContentResolver().openInputStream(uri);
			OutputStream os = new FileOutputStream(f);
			byte[] buffer = new byte[1024];
			int len;
			while ( ( len = is.read(buffer) ) != -1) {
				os.write(buffer, 0, len);
			}
			os.close();
			is.close();
			return f.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
			mException = e;
			if (f != null) {
				try {
					f.delete();
				} catch (Exception e2) {
				}
			}
		}
		return null;
	}
}
