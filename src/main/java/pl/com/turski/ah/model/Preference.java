package pl.com.turski.ah.model;

/**
 * User: Adam
 */
public class Preference {
    private String ftpServer;
    private Integer ftpPort;
    private String ftpLogin;
    private String ftpPassword;
    private String ftpPath;

    public Preference() {
    }

    public Preference(String ftpServer, Integer ftpPort, String ftpLogin, String ftpPassword, String ftpPath) {
        this.ftpServer = ftpServer;
        this.ftpPort = ftpPort;
        this.ftpLogin = ftpLogin;
        this.ftpPassword = ftpPassword;
        this.ftpPath = ftpPath;
    }

    public String getFtpServer() {
        return ftpServer;
    }

    public void setFtpServer(String ftpServer) {
        this.ftpServer = ftpServer;
    }

    public Integer getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(Integer ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpLogin() {
        return ftpLogin;
    }

    public void setFtpLogin(String ftpLogin) {
        this.ftpLogin = ftpLogin;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public String getFtpPath() {
        return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
    }
}
