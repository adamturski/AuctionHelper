package pl.com.turski.ah.view.fillTemplate;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.dialog.Dialogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.com.turski.ah.exception.CommonFileException;
import pl.com.turski.ah.model.core.TemplateValue;
import pl.com.turski.ah.service.CoreService;
import pl.com.turski.ah.view.ViewController;

import java.io.File;
import java.util.Arrays;

/**
 * User: Adam
 */
@Component
public class FillTemplateController implements ViewController {

    @FXML
    TextField titleText;
    @FXML
    TextField carMileageText;
    @FXML
    TextField carGearboxText;
    @FXML
    TextField carEngineText;
    @FXML
    TextField carPowerText;
    @FXML
    TextField carColorText;
    @FXML
    TextField carYearText;
    @FXML
    TextField galleryTitleText;
    @FXML
    TextArea equipmentsText;
    @FXML
    Node view;

    @Autowired
    private CoreService coreService;

    private String imagesDirectoryName;
    private File galleryDirectory;

    public void init(String imagesDirectoryName, File galleryDirectory) {
        resetView();
        this.imagesDirectoryName = imagesDirectoryName;
        this.galleryDirectory = galleryDirectory;
    }

    @Override
    public void resetView() {
        imagesDirectoryName = "";
        galleryDirectory = null;
        titleText.setText("");
        carMileageText.setText("");
        carGearboxText.setText("");
        carEngineText.setText("");
        carPowerText.setText("");
        carColorText.setText("");
        carYearText.setText("");
        galleryTitleText.setText("");
        equipmentsText.setText("");
    }

    public TemplateValue getTempalteValue() {
        TemplateValue templateValue = new TemplateValue();
        templateValue.setTitle(titleText.getText());
        templateValue.setCarMileage(carMileageText.getText());
        templateValue.setCarGearbox(carGearboxText.getText());
        templateValue.setCarEngine(carEngineText.getText());
        templateValue.setCarPower(carPowerText.getText());
        templateValue.setCarColor(carColorText.getText());
        templateValue.setCarYear(carYearText.getText());
        templateValue.setGalleryTitle(galleryTitleText.getText());
        templateValue.setEquipments(Arrays.asList(equipmentsText.getText().split("\n")));
        templateValue.setGalleryDirectory(galleryDirectory);
        templateValue.setRemoteGalleryName(imagesDirectoryName);
        return templateValue;
    }


    public Node getView() {
        return view;
    }

    public String getFilledTemplate() {
        try {
            return coreService.fillTemplate(getTempalteValue());
        } catch (CommonFileException e) {
            Dialogs.create().title("Błąd").message(e.getMessage()).lightweight().showError();
        }
        return null;
    }
}
