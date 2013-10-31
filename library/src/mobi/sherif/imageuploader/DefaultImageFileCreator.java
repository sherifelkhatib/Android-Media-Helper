package mobi.sherif.imageuploader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;

class DefaultImageFileCreator implements ImageFileCreator {

	@Override
	public File createImageFile() throws ImageFileCreationException {
		try {
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			String imageFileName = "Upload" + "_" + timeStamp;
			File image = File.createTempFile(imageFileName, ".jpg", getAlbumDir());
			return image;
		} catch (Exception e) {
			throw new ImageFileCreationException(e);
		}
	}

	private File getAlbumDir() {
		File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Uploads");
		if(!dir.exists()) dir.mkdirs();
		return dir;
	}
}
