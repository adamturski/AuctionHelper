package pl.com.turski.ah.model.core;

/**
 * User: Adam
 */
public enum TemplateAttr {

    TITLE("title"),
    CAR_MILEAGE("carMileage"),
    CAR_GEARBOX("carGearbox"),
    CAR_ENGINE("carEngine"),
    CAR_POWER("carPower"),
    CAR_COLOR("carColor"),
    CAR_YEAR("carYear"),
    EQUIPMENT_TBODY("equipmentTbody"),
    GALLERY_TITLE("galleryTitle"),
    GALLERY_PHOTOS("galleryPhotos"),
    RESOURCE_URL("resourceUrl"),
    IMAGE_URL("imageUrl"),
    IMAGE_HREF("imageHref");

    private String key;

    private TemplateAttr(String key) {
        this.key = key;
    }

    public String getKey() {
        return String.format("${%s}", key);
    }
}
