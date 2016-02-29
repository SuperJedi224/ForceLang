package lang.exceptions;

public class NoSuchModuleException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NoSuchModuleException(String name){
		super(name);
	}

}
