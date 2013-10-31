package mobi.sherif.imageuploader;

import java.io.File;

public interface ImageFileCreator {
	File createImageFile() throws ImageFileCreationException;
}
