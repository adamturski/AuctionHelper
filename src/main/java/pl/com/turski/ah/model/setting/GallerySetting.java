package pl.com.turski.ah.model.setting;

/**
 * User: Adam
 */
public class GallerySetting {

    private Integer imageWidth;
    private Integer thumbnailWidth;

    public GallerySetting() {
    }

    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Integer getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(Integer thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }
}
