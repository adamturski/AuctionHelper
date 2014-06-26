package pl.com.turski.ah.service;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.turski.ah.core.setting.SettingManager;
import pl.com.turski.ah.exception.*;
import pl.com.turski.ah.model.ftp.FtpConnectionStatusCode;
import pl.com.turski.ah.model.setting.FtpSetting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * User: Adam
 */
@Service
public class FtpServiceImpl implements FtpService {

    private static final Logger LOG = LoggerFactory.getLogger(FtpServiceImpl.class);

    @Autowired
    private SettingManager settingManager;
    @Autowired
    private FTPClient ftpClient;


    @Override
    public FtpConnectionStatusCode testConnection(String hostname, Integer port, String login, String password, String workingDirectory) {
        try {
            ftpClient.connect(hostname, port);
            boolean loginSucessfull = ftpClient.login(login, password);
            if (!loginSucessfull) {
                LOG.info("Logowanie nieudane podczas testu połączenia");
                LOG.error("Błąd: {}-{}", ftpClient.getReplyCode(), ftpClient.getReplyString());
                return FtpConnectionStatusCode.LOGIN_ERROR;
            }
            boolean changeWorkingDirectory = ftpClient.changeWorkingDirectory(workingDirectory);
            if (!changeWorkingDirectory) {
                LOG.info("Zmiana katalogu nieudana podczas testu połączenia");
                LOG.error("Błąd: {}-{}", ftpClient.getReplyCode(), ftpClient.getReplyString());
                return FtpConnectionStatusCode.CHANGING_WORKING_DIRECTORY_ERROR;
            }
            return FtpConnectionStatusCode.OK;
        } catch (UnknownHostException e) {
            LOG.error("Błąd UnknownHostException podczas testu połączenia", e);
            return FtpConnectionStatusCode.CONNECTION_ERROR;

        } catch (FTPConnectionClosedException e) {
            LOG.error("Błąd FTPConnectionClosedException podczas testu połączenia", e);
            return FtpConnectionStatusCode.CONNECTION_ERROR;
        } catch (IOException e) {
            LOG.error("Błąd IOException podczas testu połączenia", e);
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
    public void connect() throws FtpConnectionException, FtpLoginException {
        try {
            if (!ftpClient.isConnected()) {
                FtpSetting ftpSetting = settingManager.getFtpSetting();
                ftpClient.connect(ftpSetting.getHostname(), ftpSetting.getPort());
                boolean loggedIn = ftpClient.login(ftpSetting.getLogin(), ftpSetting.getPassword());
                if (!loggedIn) {
                    ftpClient.disconnect();
                    LOG.error("Nie udało się zalogować do serwera FTP");
                    LOG.error("Błąd: {}-{}", ftpClient.getReplyCode(), ftpClient.getReplyString());
                    throw new FtpLoginException("Nie udało się zalogować do serwera FTP. Sprawdź swoje ustawienia.");
                }
            }
        } catch (IOException e) {
            LOG.error("Nie udało się połączyć z serwerem FTP", e);
            throw new FtpConnectionException("Nie udało się połączyć z serwerem FTP. Sprawdź swoje połączenie sieciowe lub ustawienia.");
        }
    }

    @Override
    public void disconnect() {
        try {
            ftpClient.disconnect();
        } catch (IOException e) {
            LOG.error("Wystąpił błąd podczas rozłączania klienta FTP", e);
        }
    }

    @Override
    public void createGalleryDirectory(String ftpGalleryDirectory) throws FtpConnectionException, FtpLoginException, FtpDirectoryException {
        try {
            connect();
            FtpSetting ftpSetting = settingManager.getFtpSetting();
            String galleryPath = ftpSetting.getWorkingDirectory() + "/" + ftpGalleryDirectory;
            deleteDirectoryIfExist(galleryPath);
            makeDirectory(galleryPath);
        } catch (IOException e) {
            LOG.error("Błąd połączenia z serwerem FTP", e);
            throw new FtpConnectionException("Wystąpił błąd połączenia z serwrem FTP. Spróbuj ponownie.");
        }
    }

    private void deleteDirectoryIfExist(String galleryPath) throws IOException {
        boolean directoryExists = ftpClient.changeWorkingDirectory(galleryPath);
        if (directoryExists) {
            FTPFile[] ftpFiles = ftpClient.listFiles();
            if (ftpFiles != null) {
                for (FTPFile ftpFile : ftpFiles) {
                    if (ftpFile.isFile()) {
                        ftpClient.deleteFile(ftpFile.getName());
                    }
                }
            }
            ftpClient.removeDirectory(galleryPath);
        }
    }

    private void makeDirectory(String galleryPath) throws IOException, FtpDirectoryException {
        boolean directoryMaked = ftpClient.makeDirectory(galleryPath);
        if (directoryMaked) {
            boolean workingDirectoryChanged = ftpClient.changeWorkingDirectory(galleryPath);
            if (!workingDirectoryChanged) {
                LOG.error("Zmiana katalogu się nie powiodła");
                LOG.error("Błąd: {}-{}", ftpClient.getReplyCode(), ftpClient.getReplyString());
                throw new FtpDirectoryException("Nie można ustawić katalogu galerii jako katalogu roboczego. Spróbuj ponownie.");
            }
        } else {
            LOG.error("Katalog galerii nie został utworzony");
            LOG.error("Błąd: {}-{}", ftpClient.getReplyCode(), ftpClient.getReplyString());
            throw new FtpDirectoryException("Katalog galerii nie został utworzony na serwerze FTP. Spróbuj ponownie.");
        }
    }

    @Override
    public void send(File file) throws IllegalArgumentException, FtpConnectionException, FtpLoginException, FtpStoreFileException, CommonFileException {
        if (file == null) {
            throw new IllegalArgumentException("Przekazano niepoprawny plik [file=null]");
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            connect();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            boolean fileStored = ftpClient.storeFile(file.getName(), fis);
            if (!fileStored) {
                LOG.error(String.format("Nie przesłano pliku '%s'", file.getName()));
                LOG.error("Błąd: {}-{}", ftpClient.getReplyCode(), ftpClient.getReplyString());
                throw new FtpStoreFileException(String.format("Nie przesłano pliku '%s'", file.getName()));
            }
        } catch (FileNotFoundException e) {
            LOG.error("Plik przekazany do wysłania nie istnieje", e);
            throw new CommonFileException(String.format("Plik '%s' nie istnieje", file.getName()));
        } catch (IOException e) {
            LOG.error("Błąd połączenia z serwerem FTP", e);
            throw new FtpConnectionException(String.format("Wystąpił błąd połączenia z serwrem FTP podczas wysyłania pliku '%s'. Spróbuj ponownie.", file.getName()));
        }
    }


}
