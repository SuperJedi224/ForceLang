package lang.exceptions;

public class NoSuchLabelException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchLabelException(String name) {
		super(name);
	}

}
