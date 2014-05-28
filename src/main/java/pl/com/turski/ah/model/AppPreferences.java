package pl.com.turski.ah.model;

import java.util.List;

/**
 * User: Adam
 */
public class AppPreferences {
    private String ftpServer;
    private String ftpPort;
    private String login;
    private String password;
    private List<String> attributes;

    public AppPreferences(String ftpServer, String ftpPort, String login, String password) {
        this.ftpServer = ftpServer;
        this.ftpPort = ftpPort;
        this.login = login;
        this.password = password;
    }

    public String getFtpServer() {
        return ftpServer;
    }

    public void setFtpServer(String ftpServer) {
        this.ftpServer = ftpServer;
    }

    public String getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(String ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
