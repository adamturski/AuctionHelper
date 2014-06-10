package pl.com.turski.ah.model.setting;

/**
 * User: Adam
 */
public class Setting {
    private FtpSetting ftpSetting;
    private GallerySetting gallerySetting;

    public Setting() {
    }

    public FtpSetting getFtpSetting() {
        return ftpSetting;
    }

    public void setFtpSetting(FtpSetting ftpSetting) {
        this.ftpSetting = ftpSetting;
    }

    public GallerySetting getGallerySetting() {
        return gallerySetting;
    }

    public void setGallerySetting(GallerySetting gallerySetting) {
        this.gallerySetting = gallerySetting;
    }
}
