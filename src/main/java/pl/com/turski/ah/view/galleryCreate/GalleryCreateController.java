package pl.com.turski.ah.view.galleryCreate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
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

    private File rootFolder;
    private List<File> images;
    private boolean galleryCreated;

    public void initController(File rootFolder, List<File> images) {
        this.rootFolder = rootFolder;
        this.images = images;
    }

    public void galleryCreateButtonAction(ActionEvent event) {
        try {
            progressPane.setVisible(true);
            File galleryDirectory = new File(rootFolder, "upload");
            galleryDirectory.mkdir();
            imageService.generateGallery(galleryDirectory, images);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

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
