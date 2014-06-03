package pl.com.turski.ah.view.main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import pl.com.turski.ah.view.preference.PreferenceController;
import pl.com.turski.ah.view.ViewController;

/**
 * User: Adam
 */
public class MainController implements ViewController {

    @FXML
    Node view;
    @FXML
    MenuItem menuAbout;

    @Autowired
    private PreferenceController preferenceController;

    public MainController() {

    }

    public void menuPreferenceAction(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Ustawienia");
        stage.setScene(new Scene((Parent) preferenceController.getView()));
        stage.initModality(Modality.APPLICATION_MODAL);
        preferenceController.initPreferences();
        stage.showAndWait();
    }

    public void menuCloseAction(ActionEvent event) {
        Platform.exit();
    }

    public void menuAboutAction(ActionEvent event) {
//        try {
//            Parent root = FXMLLoader.load(App.class.getResource(App.VIEW_PATH + "about/about.fxml"));
//            Stage stage = new Stage();
//            stage.setTitle("O programie");
//            stage.setScene(new Scene(root));
//            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.showAndWait();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public Node getView() {
        return view;
    }
}
