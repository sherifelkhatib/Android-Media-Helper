package mobi.sherif.imageuploader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import mobi.sherif.imageuploader.MediaEngine.FileCreator;
import android.os.Environment;

class DefaultFileCreator implements FileCreator {
	String defaultExt;

	public DefaultFileCreator(String ext) {
		this.defaultExt = ext;
	}

	@Override
	public File createFile(String ext) throws FileCreationException {
		if (ext == null) ext = defaultExt;
		try {
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			String imageFileName = "Upload" + "_" + timeStamp;
			File image = File.createTempFile(imageFileName, "." + ext, getAlbumDir());
			return image;
		} catch (Exception e) {
			throw new FileCreationException(e);
		}
	}

	private File getAlbumDir( ) {
		File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Uploads");
		if ( !dir.exists()) dir.mkdirs();
		return dir;
	}
}
