package victor.bonneau.kata.bankAccount.exception;

public class ObjectNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    private String object;
    
    public ObjectNotFoundException(String object) {
		super();
		this.object = object;
	}

	@Override
    public String getMessage() {
        return String.format("%s not found.", object);
    }
}
