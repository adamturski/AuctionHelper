package pl.com.turski.ah.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.turski.ah.core.setting.SettingManager;
import pl.com.turski.ah.model.Setting;
import pl.com.turski.ah.model.exception.SettingException;
import pl.com.turski.ah.model.ftp.FtpSetting;

import java.io.IOException;

/**
 * User: Adam
 */
@Service
public class SettingServiceImpl implements SettingService {

    private static final Logger LOG = LoggerFactory.getLogger(SettingServiceImpl.class);

    @Autowired
    private SettingManager settingManager;

    @Override
    public Setting loadSettings() throws SettingException {
        try {
            return settingManager.loadSettings();
        } catch (IOException e) {
            LOG.error("Loading settings failed with IOException", e);
            throw new SettingException("Loading settings failed with IOException", e);
        }
    }

    @Override
    public void saveSettings(FtpSetting ftpSetting) throws SettingException {
        try {
            Setting setting = new Setting();
            setting.setFtpSetting(ftpSetting);
            this.settingManager.saveSettings(setting);
        } catch (IOException e) {
            LOG.error("Loading settings failed with IOException", e);
            throw new SettingException("Loading settings failed with IOException", e);
        }
    }
}
