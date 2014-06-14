package pl.com.turski.ah.view.sendToFtp;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.controlsfx.dialog.Dialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.com.turski.ah.service.FtpService;
import pl.com.turski.ah.view.ViewController;

import java.io.File;
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
    Label statusLabel;
    @FXML
    Node view;

    @Autowired
    private FtpService ftpService;

    private File imagesDirectory;
    private List<File> filesToUpload;
    private boolean done;

    public void init(File imagesDirectory, List<File> filesToUpload) {
        if (!filesToUpload.equals(this.filesToUpload)) {
            done = false;
            statusLabel.setText("");
        }
        this.imagesDirectory = imagesDirectory;
        this.filesToUpload = filesToUpload;
    }

    public void sendButtonAction(ActionEvent event) {
        Service sendImages = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        try {
                            done = false;
                            String ftpGalleryDirectory = imagesDirectory.getName();
                            ftpService.createGalleryDirectory(ftpGalleryDirectory);
                            int idx = 0;
                            for (File file : filesToUpload) {
                                updateMessage(String.format("Wysyłanie pliku %d/%d (%s)", idx + 1, filesToUpload.size(), file.getName()));
                                ftpService.send(file);
                                updateProgress(idx + 1, filesToUpload.size());
                                idx++;
                            }
                            Platform.runLater(() -> statusLabel.setText(String.format("Liczba wysłanych plików: %d", filesToUpload.size())));
                            done = true;
                        } catch (Throwable e) {
                            Platform.runLater(() -> Dialogs.create().title("Błąd").message(e.getMessage()).showError());

                        }
                        return null;
                    }
                };
            }
        };
        sendImages.start();
        Dialogs.create().title("Wysyłanie plików na serwer FTP").lightweight().showWorkerProgress(sendImages);
    }

    public Node getView() {
        return view;
    }

    public void resetView() {
        filesToUpload = null;
        imagesDirectory = null;
        done = false;
        statusLabel.setText("");
    }

    public boolean isDone() {
        return done;
    }
}
