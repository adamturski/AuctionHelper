package pl.com.turski.ah.exception;

/**
 * User: Adam
 */
public class FtpDirectoryException extends Throwable {

    public FtpDirectoryException(String message) {
        super(message);
    }

    public FtpDirectoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
