package lang.exceptions;

public class IllegalAssignmentException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public IllegalAssignmentException(String name){
		super(name);
	}

}
