package pl.com.turski.ah.view.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.oxm.XmlMappingException;
import pl.com.turski.ah.core.setting.SettingManager;
import pl.com.turski.ah.model.view.Step;
import pl.com.turski.ah.service.CoreService;
import pl.com.turski.ah.view.ViewController;
import pl.com.turski.ah.view.about.AboutController;
import pl.com.turski.ah.view.checkResults.CheckResultsController;
import pl.com.turski.ah.view.directoryChoose.DirectoryChooseController;
import pl.com.turski.ah.view.fillTemplate.FillTemplateController;
import pl.com.turski.ah.view.galleryCreate.GalleryCreateController;
import pl.com.turski.ah.view.sendToFtp.SendToFtpController;
import pl.com.turski.ah.view.setting.SettingController;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * User: Adam
 */
public class MainController implements ViewController {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    @FXML
    Node view;
    @FXML
    MenuItem menuAbout;
    @FXML
    Label stepTitle;
    @FXML
    GridPane contentGrid;
    @FXML
    FlowPane actionPanel;
    @FXML
    Button previousButton;
    @FXML
    Button nextButton;
    @FXML
    Button finishButton;

    @Autowired
    private SettingManager settingManager;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DirectoryChooseController directoryChooseController;
    @Autowired
    private GalleryCreateController galleryCreateController;
    @Autowired
    private SendToFtpController sendToFtpController;
    @Autowired
    private FillTemplateController fillTemplateController;
    @Autowired
    private CheckResultsController checkResultsController;

    private Application app;
    private Step step;

    public void init(Application app) {
        LOG.info("Inicjalizacja głównego kontrolera");
        this.app = app;
        actionPanel.getChildren().clear();
        contentGrid.getChildren().clear();
        boolean settingInitialized = initSettings();
        if (settingInitialized) {
            actionPanel.getChildren().add(nextButton);
            step = Step.DIRECTORY_CHOOSE;
            stepTitle.setText(step.getStepTitle());
            contentGrid.add(directoryChooseController.getView(), 0, 0);
        }
    }

    private boolean initSettings() {
        try {
            settingManager.loadSettings();
            return true;
        } catch (XmlMappingException e) {
            LOG.error("Wystąpił błąd mapowania pliku ustawień", e);
            Dialogs.create().title("Błąd").message("Wystąpił błąd mapowania pliku ustawień").lightweight().showError();
        } catch (IOException e) {
            LOG.error("Wystąpił błąd IO podczas wczytywania pliku ustawień");
            Dialogs.create().title("Błąd").message("Wystąpił błąd IO podczas wczytywania pliku ustawień").lightweight().showError();
        }
        return false;
    }

    public void menuSettingAction(ActionEvent event) {
        SettingController settingController = (SettingController) applicationContext.getBean("settingController");
        Stage stage = new Stage();
        stage.setTitle("Ustawienia");
        stage.setScene(new Scene((Parent) settingController.getView()));
        stage.initModality(Modality.APPLICATION_MODAL);
        settingController.init();
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

    @Override
    public void resetView() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void previousButtonAction(ActionEvent event) {
        if (step == Step.GALLERY_CREATE) {
            changeView(directoryChooseController, Step.DIRECTORY_CHOOSE, false, true, false);
        } else if (step == Step.SEND_TO_FTP) {
            changeView(galleryCreateController, Step.GALLERY_CREATE, true, true, false);
        } else if (step == Step.FILL_TEMPLATE) {
            changeView(sendToFtpController, Step.SEND_TO_FTP, true, true, false);
        } else if (step == Step.CHECK_RESULTS) {
            changeView(fillTemplateController, Step.FILL_TEMPLATE, true, true, false);
        }

    }

    public void nextButtonAction(ActionEvent event) {
        if (step == Step.DIRECTORY_CHOOSE) {
            List<File> images = directoryChooseController.getImages();
            File imagesDirectory = directoryChooseController.getImagesDirectory();
            if (imagesDirectory == null) {
                Dialogs.create().title("Błąd").message("Nie wybrałeś żadnego folderu").lightweight().showError();
            } else if (images == null || images.isEmpty()) {
                Dialogs.create().title("Błąd").message("Wybrany folder nie zawiera żadnych zdjęć").lightweight().showError();
            } else {
                galleryCreateController.init(imagesDirectory, images);
                changeView(galleryCreateController, Step.GALLERY_CREATE, true, true, false);
            }
        } else if (step == Step.GALLERY_CREATE) {
            File imagesDirectory = galleryCreateController.getImagesDirectory();
            File galleryDirectory = galleryCreateController.getGalleryDirectory();
            boolean galleryCreated = galleryCreateController.isDone();
            if (galleryCreated) {
                sendToFtpController.init(imagesDirectory, Arrays.asList(galleryDirectory.listFiles()));
                changeView(sendToFtpController, Step.SEND_TO_FTP, true, true, false);
            } else {
                Dialogs.create().title("Błąd").message("Nie wygenerowałeś galerii").lightweight().showError();
            }
        } else if (step == Step.SEND_TO_FTP) {
            boolean filesSend = sendToFtpController.isDone();
            if (filesSend) {
                fillTemplateController.init(directoryChooseController.getImagesDirectory().getName(), galleryCreateController.getGalleryDirectory());
                changeView(fillTemplateController, Step.FILL_TEMPLATE, true, true, false);
            } else {
                Dialogs.create().title("Błąd").message("Nie wysłałeś galerii na serwer").lightweight().showError();
            }
        } else if (step == Step.FILL_TEMPLATE) {
            String filledTemplate = fillTemplateController.getFilledTemplate();
            if (filledTemplate == null) {
                return;
            }
            checkResultsController.init(app, filledTemplate);
            changeView(checkResultsController, Step.CHECK_RESULTS, true, false, true);
        }
    }

    public void finishButtonAction(ActionEvent event) {
        directoryChooseController.resetView();
        galleryCreateController.resetView();
        sendToFtpController.resetView();
        fillTemplateController.resetView();
        checkResultsController.resetView();
        changeView(directoryChooseController, Step.DIRECTORY_CHOOSE, false, true, false);
    }

    public void changeView(ViewController view, Step step, boolean prev, boolean next, boolean finish) {
        this.step = step;
        stepTitle.setText(step.getStepTitle());
        contentGrid.getChildren().clear();
        contentGrid.add(view.getView(), 0, 0);
        actionPanel.getChildren().clear();
        if (prev) {
            actionPanel.getChildren().add(previousButton);
        }
        if (next) {
            actionPanel.getChildren().add(nextButton);
        }
        if (finish) {
            actionPanel.getChildren().add(finishButton);
        }
    }


}
