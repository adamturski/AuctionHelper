package pl.com.turski.ah.model;

import pl.com.turski.ah.model.ftp.FtpSetting;

/**
 * User: Adam
 */
public class Setting {
    private FtpSetting ftpSetting;

    public Setting() {
    }

    public FtpSetting getFtpSetting() {
        return ftpSetting;
    }

    public void setFtpSetting(FtpSetting ftpSetting) {
        this.ftpSetting = ftpSetting;
    }
}
