package pl.com.turski.ah.view.galleryCreate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.stage.DirectoryChooser;
import org.springframework.stereotype.Component;
import pl.com.turski.ah.util.FileUtil;
import pl.com.turski.ah.view.ViewController;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * User: Adam
 */
@Component
public class GalleryCreateController implements ViewController {

    @FXML
    Button galleryChooseButton;
    @FXML
    Label galleryPathLabel;
    @FXML
    ListView<String> galleryFileList;
    @FXML
    Node view;

    private List<File> images;

    public void init() {
        galleryFileList.setTooltip(new Tooltip("Lista zdjęć w wybranym katalogu"));
    }

    public void galleryChooseButtonAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(view.getScene().getWindow());
        if (directory != null) {
            images = Arrays.asList(directory.listFiles(FileUtil.imageFilter));
            galleryPathLabel.setText(directory.getAbsolutePath());
            ObservableList<String> fileList = FXCollections.observableArrayList();
            for (File image : images) {
                fileList.add(image.getName());
            }
            galleryFileList.setItems(fileList);
        }
    }

    public Node getView() {
        return view;
    }

    public void resetView() {
        images = null;
        galleryFileList.setItems(FXCollections.<String>emptyObservableList());
        galleryPathLabel.setText("");
    }

    public List<File> getImages() {
        return images;
    }


}
