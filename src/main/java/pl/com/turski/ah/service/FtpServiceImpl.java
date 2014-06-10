package pl.com.turski.ah.service;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.turski.ah.model.ftp.FtpConnectionStatusCode;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

/**
 * User: Adam
 */
@Service
public class FtpServiceImpl implements FtpService {

    private static final Logger LOG = LoggerFactory.getLogger(FtpServiceImpl.class);

    @Autowired
    private SettingService settingService;
    @Autowired
    private FTPClient ftpClient;

    @Override
    public FtpConnectionStatusCode testConnection(String hostname, Integer port, String login, String password, String workingDirectory) {
        try {
            ftpClient.connect(hostname,port);
            boolean loginSucessfull = ftpClient.login(login, password);
            if (!loginSucessfull) {
                LOG.info("Ftp client cannot login during connection test");
                return FtpConnectionStatusCode.LOGIN_ERROR;
            }
            boolean changeWorkingDirectory = ftpClient.changeWorkingDirectory(workingDirectory);
            if (!changeWorkingDirectory) {
                LOG.info("Ftp client cannot change working directory during connection test");
                return FtpConnectionStatusCode.CHANGING_WORKING_DIRECTORY_ERROR;
            }
            return FtpConnectionStatusCode.OK;
        } catch (UnknownHostException e) {
            LOG.error("UnknownHostException occured during connection test", e);
            return FtpConnectionStatusCode.CONNECTION_ERROR;

        } catch (FTPConnectionClosedException e) {
            LOG.error("FTPConnectionClosedException occured during connection test", e);
            return FtpConnectionStatusCode.CONNECTION_ERROR;
        } catch (IOException e) {
            LOG.error("IOException occured during connection test", e);
            return FtpConnectionStatusCode.CONNECTION_ERROR;
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                LOG.error("Cannot disconnect ftp client", e);
            }
        }
    }

    @Override
    public void upload(List<BufferedImage> images) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
