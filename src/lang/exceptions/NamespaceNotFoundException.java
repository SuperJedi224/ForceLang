package lang.exceptions;

public class NamespaceNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NamespaceNotFoundException(String name){
		super(name);
	}

}
