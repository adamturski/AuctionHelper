package pl.com.turski.ah.exception;

/**
 * User: Adam
 */
public class FtpConnectionException extends Throwable {

    public FtpConnectionException(String message) {
        super(message);
    }

    public FtpConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
