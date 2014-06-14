package pl.com.turski.ah.exception;

/**
 * User: Adam
 */
public class FtpLoginException extends Throwable {

    public FtpLoginException(String message) {
        super(message);
    }

    public FtpLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
