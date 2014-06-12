package pl.com.turski.ah.service;

import pl.com.turski.ah.model.ftp.FtpConnectionStatusCode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * User: Adam
 */
public interface FtpService {

    public FtpConnectionStatusCode testConnection(String hostname, Integer port, String login, String password, String workingDirectory);

    public void upload(List<BufferedImage> images);

    public void upload(File file) throws IllegalArgumentException, IOException;
}
