package pl.com.turski.ah.view.galleryCreate;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import pl.com.turski.ah.service.ImageService;
import pl.com.turski.ah.view.ViewController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * User: Adam
 */
@Component
public class GalleryCreateController implements ViewController, Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(GalleryCreateController.class);

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

    @Autowired
    private ImageService imageService;

    private File imagesDirectory;
    private List<File> images;
    private File galleryDirectory;
    private boolean galleryCreated;

    private Service createGallery;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createGallery = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        try {
                            imageService.cleanDirectory(galleryDirectory);
                            int idx = 0;
                            for (File image : images) {
                                updateMessage(String.format("Pzetwarzanie zdjęcia %d/%d (%s)", idx + 1, images.size(), image.getName()));
                                imageService.processImage(galleryDirectory, image);
                                if (idx == 0 && images.size() == 1) {
                                    imageService.createGallery(galleryDirectory, image, null, null);
                                } else if (idx == 0 && images.size() > 1) {
                                    imageService.createGallery(galleryDirectory, image, null, images.get(idx + 1));
                                } else if (idx == images.size() - 1 && images.size() > 1) {
                                    imageService.createGallery(galleryDirectory, image, images.get(idx - 1), null);
                                } else {
                                    imageService.createGallery(galleryDirectory, image, images.get(idx - 1), images.get(idx + 1));
                                }
                                updateProgress(idx++, images.size());
                            }
                            updateMessage(String.format("Liczba przetworzonych zdjęć: %d", images.size()));
                            galleryCreated = true;
                        } catch (IOException e) {
                            LOG.error("Wystąpił błąd podczas tworzenia galerii", e);
                            Dialogs.create().title("Błąd").message("Wystąpił błąd podczas tworzenia galerii").lightweight().showError();
                        }
                        return null;
                    }
                };
            }
        };
        createGallery.setOnSucceeded(workerStateEvent -> progressBar.setVisible(false));
        progressBar.progressProperty().bind(createGallery.progressProperty());
        progressInfoLabel.textProperty().bind(createGallery.messageProperty());
    }

    public void init(File imagesDirectory, List<File> images) {
        if (!imagesDirectory.equals(this.imagesDirectory)) {
            galleryCreated = false;
        }
        this.imagesDirectory = imagesDirectory;
        this.images = images;
    }

    public void galleryCreateButtonAction(ActionEvent event) {
        createGalleryDirectory();
        createGallery();
    }

    private void createGallery() {
        progressBar.setVisible(true);
        if (!createGallery.isRunning()) {
            createGallery.reset();
            createGallery.start();
        }
    }

    private void createGalleryDirectory() {
        try {
            galleryCreated = false;
            galleryDirectory = new File(imagesDirectory, "galeria");
            boolean galleryDirectoryCreated = galleryDirectory.mkdir();
            if (galleryDirectoryCreated) {
                LOG.info("Utworzono katalog galerii");
            } else {
                LOG.warn("Nie utworzono katalogu galerii. Być może taki katalog istnieje");
            }
        } catch (SecurityException e) {
            LOG.error("Wystąpił błąd podczas tworzenia katalogu galerii", e);
            Dialogs.create().title("Błąd").message("Wystąpił błąd podczas tworzenia katalogu galerii").lightweight().showError();
        }
    }

    public Node getView() {
        return view;
    }

    public void resetView() {
        imagesDirectory = null;
        images = null;
        galleryDirectory = null;
        galleryCreated = false;
        progressPane.setVisible(false);
        progressInfoLabel.setText("");
        progressBar.setProgress(0);
    }

    public boolean isGalleryCreated() {
        return galleryCreated;
    }
}
