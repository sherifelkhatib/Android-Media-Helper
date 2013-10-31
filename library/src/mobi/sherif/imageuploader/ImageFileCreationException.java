package mobi.sherif.imageuploader;

public class ImageFileCreationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 153850828158406491L;

	public ImageFileCreationException() {
		super();
	}
	public ImageFileCreationException(Exception original) {
		super();
	}
	Exception mException;
	public Exception getOriginalException() {
		return mException;
	}
}
