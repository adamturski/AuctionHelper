package pl.com.turski.ah.exception;

/**
 * User: Adam
 */
public class FtpStoreFileException extends Throwable {

    public FtpStoreFileException(String message) {
        super(message);
    }

    public FtpStoreFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
