package pl.com.turski.ah.core.setting;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Component;
import pl.com.turski.ah.model.setting.Setting;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * User: Adam
 */
@Component
public class SettingManager {

    private String settingFileName;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public void setSettingFileName(String settingFileName) {
        this.settingFileName = settingFileName;
    }

    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    public void saveSettings(Setting setting) throws IOException {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(settingFileName);
            this.marshaller.marshal(setting, new StreamResult(os));
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    public Setting loadSettings() throws IOException {
        FileInputStream is = null;
        try {
            is = new FileInputStream(settingFileName);
            return (Setting) this.unmarshaller.unmarshal(new StreamSource(is));
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
