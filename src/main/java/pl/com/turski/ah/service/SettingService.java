package pl.com.turski.ah.service;

import pl.com.turski.ah.model.setting.Setting;
import pl.com.turski.ah.model.exception.SettingException;
import pl.com.turski.ah.model.setting.FtpSetting;

/**
 * User: Adam
 */
public interface SettingService {
    public Setting loadSettings() throws SettingException;

    public void saveSettings(FtpSetting ftpSetting) throws SettingException;
}
