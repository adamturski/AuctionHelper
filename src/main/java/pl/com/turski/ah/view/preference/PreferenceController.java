package pl.com.turski.ah.view.preference;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.com.turski.ah.model.Preference;
import pl.com.turski.ah.service.FtpService;
import pl.com.turski.ah.service.SettingService;
import pl.com.turski.ah.view.ViewController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * User: Adam
 */
@Component
public class PreferenceController implements ViewController, Initializable {

    @FXML
    Node view;
    @FXML
    TextField ftpServer;
    @FXML
    TextField ftpPort;
    @FXML
    TextField ftpLogin;
    @FXML
    PasswordField ftpPassword;
    @FXML
    TextField ftpGalleryFolder;
    @FXML
    Label connectionStatus;
    @FXML
    Label templateFilePath;
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

    public void initPreferences() {
        Preference preference = settingService.getPreference();
        ftpServer.setText(preference.getFtpServer());
        ftpPort.setText(String.valueOf(preference.getFtpPort()));
        ftpLogin.setText(preference.getFtpLogin());
        ftpPassword.setText(preference.getFtpPassword());
        ftpGalleryFolder.setText(preference.getFtpPath());
    }

    public void testButtonAction(ActionEvent event) {
        System.out.print("asd");
        //To change body of created methods use File | Settings | File Templates.
    }

    public void templateLoadButtonAction(ActionEvent event) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void newVariableButtonAction(ActionEvent event) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void saveButtonAction(ActionEvent event) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void cancelButtonAction(ActionEvent event) {
        //To change body of created methods use File | Settings | File Templates.
    }

    @Override
    public Node getView() {
        return view;
    }


}
