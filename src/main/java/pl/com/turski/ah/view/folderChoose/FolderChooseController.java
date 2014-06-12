package pl.com.turski.ah.view.folderChoose;

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
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.springframework.stereotype.Component;
import pl.com.turski.ah.view.ViewController;

import java.io.File;
import java.io.FileFilter;
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
    ListView<String> folderFileList;
    @FXML
    Node view;

    private List<File> images;
    private File rootDirectory;

    public void init() {
        folderFileList.setTooltip(new Tooltip("Lista zdjęć w wybranym katalogu"));
    }

    public void folderChooseButtonAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(view.getScene().getWindow());
        if (directory != null) {
            rootDirectory = directory;
            images = Arrays.asList(directory.listFiles((FileFilter) new SuffixFileFilter(Arrays.asList(".jpg", ".jpeg"))));
            folderPathLabel.setText(directory.getAbsolutePath());
            ObservableList<String> fileList = FXCollections.observableArrayList();
            for (File image : images) {
                fileList.add(image.getName());
            }
            folderFileList.setItems(fileList);
        }
    }

    public Node getView() {
        return view;
    }

    public void resetView() {
        images = null;
        folderFileList.setItems(FXCollections.<String>emptyObservableList());
        folderPathLabel.setText("");
    }

    public List<File> getImages() {
        return images;
    }

    public File getRootDirectory() {
        return rootDirectory;
    }
}
