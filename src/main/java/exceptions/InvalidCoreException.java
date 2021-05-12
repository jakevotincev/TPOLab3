package exceptions;

public class InvalidCoreException extends IllegalArgumentException {
    String message;

    public InvalidCoreException(String message){
        this.message = message;
    }

    public InvalidCoreException() {
    }

    public String getMessage() {
        return message;
    }
}
