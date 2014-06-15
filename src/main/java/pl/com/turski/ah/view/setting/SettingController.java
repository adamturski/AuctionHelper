package pl.com.turski.ah.view.setting;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pl.com.turski.ah.core.setting.SettingManager;
import pl.com.turski.ah.model.ftp.FtpConnectionStatusCode;
import pl.com.turski.ah.model.setting.FtpSetting;
import pl.com.turski.ah.model.setting.GallerySetting;
import pl.com.turski.ah.service.FtpService;
import pl.com.turski.ah.view.ViewController;

import java.io.IOException;

/**
 * User: Adam
 */
public class SettingController implements ViewController {

    private static final Logger LOG = LoggerFactory.getLogger(SettingController.class);

    @FXML
    Node view;
    @FXML
    TextField ftpHostname;
    @FXML
    TextField ftpPort;
    @FXML
    TextField ftpLogin;
    @FXML
    PasswordField ftpPassword;
    @FXML
    TextField ftpWorkingDirectory;
    @FXML
    TextField galleriesDirectory;
    @FXML
    TextField resourcesUrlText;
    @FXML
    TextField imageWidth;
    @FXML
    TextField thumbnailWidth;
    @FXML
    Label connectionStatus;

    @Autowired
    private SettingManager settingManager;
    @Autowired
    private FtpService ftpService;

    public void init() {
        resetView();
        FtpSetting ftpSetting = settingManager.getFtpSetting();
        ftpHostname.setText(ftpSetting.getHostname());
        ftpPort.setText(String.valueOf(ftpSetting.getPort()));
        ftpLogin.setText(ftpSetting.getLogin());
        ftpPassword.setText(ftpSetting.getPassword());
        ftpWorkingDirectory.setText(ftpSetting.getWorkingDirectory());
        GallerySetting gallerySetting = settingManager.getGallerySetting();
        galleriesDirectory.setText(gallerySetting.getGalleriesDirectory());
        resourcesUrlText.setText(gallerySetting.getResourceUrl());
        imageWidth.setText(String.valueOf(gallerySetting.getImageWidth()));
        thumbnailWidth.setText(String.valueOf(gallerySetting.getThumbnailWidth()));
    }

    @Override
    public Node getView() {
        return view;
    }

    @Override
    public void resetView() {
    }

    public void testButtonAction(ActionEvent event) {
        FtpConnectionStatusCode statusCode = ftpService.testConnection(ftpHostname.getText(), Integer.parseInt(ftpPort.getText()), ftpLogin.getText(), ftpPassword.getText(), ftpWorkingDirectory.getText());
        connectionStatus.setText(statusCode.getMessage());
    }

    public void saveButtonAction(ActionEvent event) {
        try {
            FtpSetting ftpSetting = new FtpSetting();
            ftpSetting.setHostname(ftpHostname.getText());
            ftpSetting.setPort(Integer.parseInt(ftpPort.getText()));
            ftpSetting.setLogin(ftpLogin.getText());
            ftpSetting.setPassword(ftpPassword.getText());
            ftpSetting.setWorkingDirectory(ftpWorkingDirectory.getText());
            GallerySetting gallerySetting = new GallerySetting();
            gallerySetting.setGalleriesDirectory(galleriesDirectory.getText());
            gallerySetting.setResourceUrl(resourcesUrlText.getText());
            gallerySetting.setImageWidth(Integer.parseInt(imageWidth.getText()));
            gallerySetting.setThumbnailWidth(Integer.parseInt(thumbnailWidth.getText()));
            settingManager.saveSettings(ftpSetting, gallerySetting);
            Stage stage = (Stage) view.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            LOG.error("Wystąpił błąd podczas zapisywania ustawień", e);
            Dialogs.create().title("Błąd").message("Wystąpił błąd podczas zapisywania ustawień").lightweight().showError();
        }
    }

    public void cancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) view.getScene().getWindow();
        stage.close();
    }
}
