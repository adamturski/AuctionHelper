package pl.com.turski.ah.view.checkResults;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.controlsfx.dialog.Dialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.com.turski.ah.exception.CommonFileException;
import pl.com.turski.ah.service.CoreService;
import pl.com.turski.ah.view.ViewController;

import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * User: Adam
 */
@Component
public class CheckResultsController implements ViewController, Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(CheckResultsController.class);

    @FXML
    Button copyToClipboardButton;
    @FXML
    Button showInBrowserButton;
    @FXML
    TextArea filledTemplateText;
    @FXML
    Node view;

    @Autowired
    private CoreService coreService;

    private Application app;
    private StringProperty filledTemplateProperty = new SimpleStringProperty();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filledTemplateText.textProperty().bind(filledTemplateProperty);
    }

    public void init(Application app, String filledTemplate) {
        resetView();
        this.app = app;
        filledTemplateProperty.set(filledTemplate);
    }

    public Node getView() {
        return view;
    }

    public void resetView() {
        filledTemplateProperty.set("");
    }

    public void copyToClipboardAction(ActionEvent event) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(filledTemplateProperty.get());
        clipboard.setContent(content);
    }

    public void showInBrowserAction(ActionEvent event) {
        try {
            URI uri = coreService.createTemplateFile(filledTemplateProperty.get());
            app.getHostServices().showDocument(uri.toString());
        } catch (CommonFileException e) {
            Dialogs.create().title("Błąd").message(e.getMessage()).lightweight().showError();
        }
    }
}
