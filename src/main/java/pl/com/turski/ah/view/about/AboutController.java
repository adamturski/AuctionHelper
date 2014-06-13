package pl.com.turski.ah.view.about;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import pl.com.turski.ah.view.ViewController;

/**
 * User: Adam
 */
public class AboutController implements ViewController {

    @FXML
    Node view;
    @FXML
    Button closeButton;

    @FXML
    public void closeButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public Node getView() {
        return view;
    }

    @Override
    public void resetView() {

    }
}
