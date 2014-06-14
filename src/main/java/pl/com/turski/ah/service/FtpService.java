package pl.com.turski.ah.service;

import pl.com.turski.ah.exception.*;
import pl.com.turski.ah.model.ftp.FtpConnectionStatusCode;

import java.io.File;
import java.io.IOException;

/**
 * User: Adam
 */
public interface FtpService {

    public FtpConnectionStatusCode testConnection(String hostname, Integer port, String login, String password, String workingDirectory);

    public void connect() throws FtpConnectionException, FtpLoginException;

    public void createGalleryDirectory(String ftpGalleryDirectory) throws FtpConnectionException, FtpLoginException, FtpDirectoryException;

    public void send(File file) throws IOException, FtpConnectionException, FtpLoginException, CommonFileException, FtpStoreFileException;

    public void disconnect();
}
