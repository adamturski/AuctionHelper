package pl.com.turski.ah.model.ftp;

/**
 * User: Adam
 */
public enum FtpConnectionStatusCode {
    CONNECTION_ERROR("Błąd podczas połączenia z serwerem FTP"), LOGIN_ERROR("Błąd podczas logowania do serwera FTP"),CHANGING_WORKING_DIRECTORY_ERROR("Błąd podczas zmiany katalogu roboczego"), OK("Pomyślnie połączono z serwerem FTP");


    private String message;

    private FtpConnectionStatusCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
