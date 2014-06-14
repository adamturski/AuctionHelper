package pl.com.turski.ah.exception;

/**
 * User: Adam
 */
public class CommonFileException extends Throwable {

    public CommonFileException(String message) {
        super(message);
    }

    public CommonFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
