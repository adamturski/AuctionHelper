package pl.com.turski.ah.core.image;

/**
 * User: Adam
 */
public enum GalleryTemplateAttr {

    TITLE("title"),
    PREVIOUS_LINK_HREF("previousLinkHref"),
    PREVIOUS_LINK_DISPLAY("previousLinkDisplay"),
    IMAGE_SRC("imageSrc"),
    IMAGE_WIDTH("imageWidth"),
    NEXT_LINK_HREF("nextLinkHref"),
    NEXT_LINK_DISPLAY("nextLinkDisplay");

    private String key;

    private GalleryTemplateAttr(String key) {
        this.key = key;
    }

    public String getKey() {
        return String.format("${%s}", key);
    }
}
