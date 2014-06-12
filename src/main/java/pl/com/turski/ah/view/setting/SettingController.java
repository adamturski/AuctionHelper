package pl.com.turski.ah.view.setting;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pl.com.turski.ah.model.setting.GallerySetting;
import pl.com.turski.ah.model.setting.Setting;
import pl.com.turski.ah.model.exception.SettingException;
import pl.com.turski.ah.model.ftp.FtpConnectionStatusCode;
import pl.com.turski.ah.model.setting.FtpSetting;
import pl.com.turski.ah.service.FtpService;
import pl.com.turski.ah.service.SettingService;
import pl.com.turski.ah.view.ViewController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * User: Adam
 */
public class SettingController implements ViewController, Initializable {

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
    Label connectionStatus;
    @FXML
    Label templateFilePath;
    @FXML
    TextField imageWidth;
    @FXML
    TextField thumbnailWidth;
    @FXML
    TableView attributesTable;
    @FXML
    TableColumn attributeNameColumn;
    @FXML
    TableColumn attributeDefaultValueColumn;
    @FXML
    TableColumn attributeOrderColumn;
    @FXML
    TableView variablesTable;
    @FXML
    TableColumn variableNameColumn;
    @FXML
    TableColumn variableValueColumn;
    @FXML
    TableColumn variableActionColumn;
    @FXML
    TextField newVariableName;
    @FXML
    TextField newVariableValue;

    @Autowired
    private SettingService settingService;
    @Autowired
    private FtpService ftpService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        attributesTable.setPlaceholder(new Label("Nie znaleziono żadnych atrybutów w szablonie"));
        variablesTable.setPlaceholder(new Label("Nie zdefiniowano żadnych zmiennych"));
    }

    public void initSetting() {
        try {
            Setting setting = settingService.loadSettings();
            FtpSetting ftpSetting = setting.getFtpSetting();
            ftpHostname.setText(ftpSetting.getHostname());
            ftpPort.setText(String.valueOf(ftpSetting.getPort()));
            ftpLogin.setText(ftpSetting.getLogin());
            ftpPassword.setText(ftpSetting.getPassword());
            ftpWorkingDirectory.setText(ftpSetting.getWorkingDirectory());
            GallerySetting gallerySetting = setting.getGallerySetting();
            imageWidth.setText(String.valueOf(gallerySetting.getImageWidth()));
            thumbnailWidth.setText(String.valueOf(gallerySetting.getThumbnailWidth()));
        } catch (SettingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void testButtonAction(ActionEvent event) {
        FtpConnectionStatusCode statusCode = ftpService.testConnection(ftpHostname.getText(), Integer.parseInt(ftpPort.getText()), ftpLogin.getText(), ftpPassword.getText(), ftpWorkingDirectory.getText());
        connectionStatus.setText(statusCode.getMessage());
    }

    public void templateLoadButtonAction(ActionEvent event) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void newVariableButtonAction(ActionEvent event) {
        //To change body of created methods use File | Settings | File Templates.
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
            gallerySetting.setImageWidth(Integer.parseInt(imageWidth.getText()));
            gallerySetting.setThumbnailWidth(Integer.parseInt(thumbnailWidth.getText()));
            settingService.saveSettings(ftpSetting, gallerySetting);
        } catch (SettingException e) {
            LOG.error("SettingException occured during save settings", e);
        }
    }

    public void cancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) view.getScene().getWindow();
        stage.close();
    }

    @Override
    public Node getView() {
        return view;
    }


}
