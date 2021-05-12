package exceptions;

public class InvalidPropertiesException extends InvalidCoreException{
    public InvalidPropertiesException(String message) {
        super(message);
    }

    public InvalidPropertiesException() {
        super.message = "Invalid config file";
    }
}
