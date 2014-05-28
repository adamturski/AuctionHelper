package pl.com.turski.ah.view.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.com.turski.ah.main.Main;

import java.io.IOException;

/**
 * User: Adam
 */
public class MainController {
    @FXML
    private MenuItem menuAbout;

    public void menuAboutAction(ActionEvent event) {
        try {
            // Load the fxml file and create a new stage for the popup
            Parent root = FXMLLoader.load(Main.class.getResource(Main.VIEW_PATH + "about/about.fxml"));
            Stage stage = new Stage();
            stage.setTitle("O programie");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
