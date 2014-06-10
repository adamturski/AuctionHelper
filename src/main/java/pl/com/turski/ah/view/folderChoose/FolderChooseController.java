package pl.com.turski.ah.view.folderChoose;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
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
public class FolderChooseController implements ViewController {

    @FXML
    Button folderChooseButton;
    @FXML
    Label folderPathLabel;
    @FXML
    ListView<String> fodlerFileList;
    @FXML
    Node view;

    private List<File> images;

    public void init() {
        fodlerFileList.setTooltip(new Tooltip("Lista zdjęć w wybranym katalogu"));
    }

    public void folderChooseButtonAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(view.getScene().getWindow());
        if (directory != null) {
            images = Arrays.asList(directory.listFiles(FileUtil.imageFilter));
            folderPathLabel.setText(directory.getAbsolutePath());
            ObservableList<String> fileList = FXCollections.observableArrayList();
            for (File image : images) {
                fileList.add(image.getName());
            }
            fodlerFileList.setItems(fileList);
        }
    }

    public Node getView() {
        return view;
    }

    public void resetView() {
        images = null;
        fodlerFileList.setItems(FXCollections.<String>emptyObservableList());
        folderPathLabel.setText("");
    }

    public List<File> getImages() {
        return images;
    }


}
