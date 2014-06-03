package pl.com.turski.ah.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.turski.ah.core.setting.Setting;
import pl.com.turski.ah.model.Preference;
import pl.com.turski.ah.model.exception.SettingException;

import java.io.IOException;

/**
 * User: Adam
 */
@Service
public class SettingServiceImpl implements SettingService {

    private static final Logger LOG = LoggerFactory.getLogger(SettingServiceImpl.class);

    @Autowired
    private Setting setting;

    @Override
    public void loadSettings() throws SettingException {
        LOG.info("Loading settings");
        try {
            setting.loadSettings();
        } catch (IOException e) {
            LOG.error("Loading settings failed with IOException", e);
            throw new SettingException("Loading settings failed with IOException", e);
        }
    }

    @Override
    public void saveSettings() throws SettingException {
        LOG.info("Saving settings");
        try {
            setting.saveSettings();
        } catch (IOException e) {
            LOG.error("Loading settings failed with IOException", e);
            throw new SettingException("Loading settings failed with IOException", e);
        }
    }

    @Override
    public Preference getPreference() {
        return setting.getPreference();
    }
}
