package pl.com.turski.ah.model.core;

import java.io.File;
import java.util.List;

/**
 * User: Adam
 */
public class TemplateValue {
    private String title;
    private String carMileage;
    private String carGearbox;
    private String carEngine;
    private String carPower;
    private String carColor;
    private String carYear;
    private List<String> equipments;
    private String galleryTitle;

    private File galleryDirectory;
    private String remoteGalleryName;

    public TemplateValue() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCarMileage() {
        return carMileage;
    }

    public void setCarMileage(String carMileage) {
        this.carMileage = carMileage;
    }

    public String getCarGearbox() {
        return carGearbox;
    }

    public void setCarGearbox(String carGearbox) {
        this.carGearbox = carGearbox;
    }

    public String getCarEngine() {
        return carEngine;
    }

    public void setCarEngine(String carEngine) {
        this.carEngine = carEngine;
    }

    public String getCarPower() {
        return carPower;
    }

    public void setCarPower(String carPower) {
        this.carPower = carPower;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarYear() {
        return carYear;
    }

    public void setCarYear(String carYear) {
        this.carYear = carYear;
    }

    public List<String> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<String> equipments) {
        this.equipments = equipments;
    }

    public String getGalleryTitle() {
        return galleryTitle;
    }

    public void setGalleryTitle(String galleryTitle) {
        this.galleryTitle = galleryTitle;
    }

    public File getGalleryDirectory() {
        return galleryDirectory;
    }

    public void setGalleryDirectory(File galleryDirectory) {
        this.galleryDirectory = galleryDirectory;
    }

    public String getRemoteGalleryName() {
        return remoteGalleryName;
    }

    public void setRemoteGalleryName(String remoteGalleryName) {
        this.remoteGalleryName = remoteGalleryName;
    }
}
