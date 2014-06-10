package pl.com.turski.ah.view.main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import pl.com.turski.ah.model.view.Step;
import pl.com.turski.ah.view.ViewController;
import pl.com.turski.ah.view.about.AboutController;
import pl.com.turski.ah.view.galleryChoose.GalleryChooseController;
import pl.com.turski.ah.view.setting.SettingController;

import java.io.File;
import java.util.List;

/**
 * User: Adam
 */
public class MainController implements ViewController {

    @FXML
    Node view;
    @FXML
    MenuItem menuAbout;
    @FXML
    Label stepTitle;
    @FXML
    GridPane contentGrid;
    @FXML
    HBox stepsHBox;
    @FXML
    Button previousButton;
    @FXML
    Button nextButton;
    @FXML
    Button finishButton;

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private GalleryChooseController galleryChooseController;

    private Step step;

    public void init() {
        contentGrid.getChildren().clear();
        stepsHBox.getChildren().clear();
        stepsHBox.getChildren().add(nextButton);
        step = Step.GALLERY_CHOOSE;
        stepTitle.setText(step.getStepTitle());
        contentGrid.add(galleryChooseController.getView(), 0, 0);
    }

    public void menuSettingAction(ActionEvent event) {
        SettingController settingController = (SettingController) applicationContext.getBean("settingController");
        Stage stage = new Stage();
        stage.setTitle("Ustawienia");
        stage.setScene(new Scene((Parent) settingController.getView()));
        stage.initModality(Modality.APPLICATION_MODAL);
        settingController.initSetting();
        stage.showAndWait();
    }

    public void menuCloseAction(ActionEvent event) {
        Platform.exit();
    }

    public void menuAboutAction(ActionEvent event) {
        AboutController aboutController = (AboutController) applicationContext.getBean("aboutController");
        Stage stage = new Stage();
        stage.setTitle("O programie");
        stage.setScene(new Scene((Parent) aboutController.getView()));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @Override
    public Node getView() {
        return view;
    }

    public void previousButtonAction(ActionEvent event) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void nextButtonAction(ActionEvent event) {
        List<File> images = galleryChooseController.getImages();
    }

    public void finishButtonAction(ActionEvent event) {
        //To change body of created methods use File | Settings | File Templates.
    }


}
