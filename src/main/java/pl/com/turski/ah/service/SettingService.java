package pl.com.turski.ah.service;

import pl.com.turski.ah.exception.SettingException;
import pl.com.turski.ah.model.setting.FtpSetting;
import pl.com.turski.ah.model.setting.GallerySetting;

/**
 * User: Adam
 */
public interface SettingService {
    public void loadSettings() throws SettingException;

    public void saveSettings(FtpSetting ftpSetting, GallerySetting gallerySetting) throws SettingException;
}
