package pl.com.turski.ah.spring;


import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * User: Adam
 */
public class FxmlLoader {

    private String viewPath;

    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }

    public Object load(String url) throws IOException {
        InputStream fxmlStream = null;
        try {
            fxmlStream = getClass().getResourceAsStream(viewPath + url);
            FXMLLoader loader = new FXMLLoader();
            loader.load(fxmlStream);
            return loader.getController();
        } finally {
            if (fxmlStream != null) {
                fxmlStream.close();
            }
        }
    }
}
