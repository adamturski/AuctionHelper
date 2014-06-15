package pl.com.turski.ah.view.galleryCreate;

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
import pl.com.turski.ah.service.ImageService;
import pl.com.turski.ah.view.ViewController;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * User: Adam
 */
@Component
public class GalleryCreateController implements ViewController {

    private static final Logger LOG = LoggerFactory.getLogger(GalleryCreateController.class);

    @FXML
    Button galleryCreateButton;
    @FXML
    Label statusLabel;
    @FXML
    Node view;

    @Autowired
    private ImageService imageService;

    private File imagesDirectory;
    private List<File> images;
    private File galleryDirectory;
    private boolean done;

    public void init(File imagesDirectory, List<File> images) {
        resetView();
        this.imagesDirectory = imagesDirectory;
        this.images = images;
    }

    public void galleryCreateButtonAction(ActionEvent event) {
        createGalleryDirectory();
        createGallery();
    }

    private void createGalleryDirectory() {
        try {
            done = false;
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

    private void createGallery() {
        Service createGallery = new Service() {
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
                                updateProgress(idx + 1, images.size());
                                idx++;
                            }
                            Platform.runLater(() -> statusLabel.setText(String.format("Liczba przetworzonych zdjęć: %d", images.size())));
                            done = true;
                        } catch (IOException e) {
                            LOG.error("Wystąpił błąd podczas tworzenia galerii", e);
                            Dialogs.create().title("Błąd").message("Wystąpił błąd podczas tworzenia galerii").lightweight().showError();
                        }
                        return null;
                    }
                };
            }
        };
        createGallery.start();
        Dialogs.create().title("Generowanie galerii").lightweight().showWorkerProgress(createGallery);
    }

    public Node getView() {
        return view;
    }

    public void resetView() {
        imagesDirectory = null;
        images = null;
        done = false;
        statusLabel.setText("");
    }

    public File getImagesDirectory() {
        return imagesDirectory;
    }

    public File getGalleryDirectory() {
        return galleryDirectory;
    }

    public boolean isDone() {
        return done;
    }
}
