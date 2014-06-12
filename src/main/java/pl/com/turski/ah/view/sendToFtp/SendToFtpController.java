package pl.com.turski.ah.view.sendToFtp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import org.controlsfx.dialog.Dialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.com.turski.ah.service.FtpService;
import pl.com.turski.ah.view.ViewController;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * User: Adam
 */
@Component
public class SendToFtpController implements ViewController {

    private static final Logger LOG = LoggerFactory.getLogger(SendToFtpController.class);

    @FXML
    Button sendButton;
    @FXML
    VBox progressPane;
    @FXML
    Label progressInfoLabel;
    @FXML
    ProgressBar progressBar;
    @FXML
    Node view;

    @Autowired
    private FtpService ftpService;

    private List<File> filesToUpload;
    private boolean done;

    public void init(List<File> filesToUpload) {
        this.filesToUpload = filesToUpload;
    }

    public void sendButtonAction(ActionEvent event) {
        try {
            if(filesToUpload==null){
                LOG.error("Illegal state: filesToUpload=null");
                Dialogs.create().title("Błąd aplikacji").message("Nie przekazano listy plików do wysłania").showError();
            }
            progressPane.setVisible(true);
            ftpService.upload(filesToUpload.get(0));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public Node getView() {
        return view;
    }

    public void resetView() {
        filesToUpload = null;
        done = false;
        progressPane.setVisible(false);
        progressInfoLabel.setText("");
        progressBar.setProgress(0);
    }
}
