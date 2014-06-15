package pl.com.turski.ah.model.setting;

/**
 * User: Adam
 */
public class GallerySetting {

    private String galleriesDirectory;
    private String resourceUrl;
    private Integer imageWidth;
    private Integer thumbnailWidth;

    public GallerySetting() {
    }

    public String getGalleriesDirectory() {
        return galleriesDirectory;
    }

    public void setGalleriesDirectory(String galleriesDirectory) {
        this.galleriesDirectory = galleriesDirectory;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
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
