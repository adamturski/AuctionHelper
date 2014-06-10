package pl.com.turski.ah.util;

import java.io.File;

/**
 * User: Adam
 */
public class ImageUtil {

    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

}
