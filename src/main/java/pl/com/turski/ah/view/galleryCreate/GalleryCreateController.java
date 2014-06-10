package pl.com.turski.ah.view.galleryCreate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;
import pl.com.turski.ah.view.ViewController;

/**
 * User: Adam
 */
@Component
public class GalleryCreateController implements ViewController {

    @FXML
    Button galleryCreateButton;
    @FXML
    VBox progressPane;
    @FXML
    Label progressInfoLabel;
    @FXML
    ProgressBar progressBar;
    @FXML
    Node view;

    private boolean galleryCreated;

    public void galleryCreateButtonAction(ActionEvent event) {

        galleryCreated = true;
    }

    public Node getView() {
        return view;
    }

    public void resetView() {
        galleryCreated = false;
        progressPane.setVisible(false);
        progressInfoLabel.setText("");
        progressBar.setProgress(0);
    }
}
