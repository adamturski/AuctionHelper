package pl.com.turski.ah.service;

import pl.com.turski.ah.model.Preference;
import pl.com.turski.ah.model.exception.SettingException;

/**
 * User: Adam
 */
public interface SettingService {
    public void loadSettings() throws SettingException;

    public void saveSettings() throws SettingException;

    public Preference getPreference();
}
