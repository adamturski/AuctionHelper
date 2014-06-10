package pl.com.turski.ah.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * User: Adam
 */
public class FileUtil {
    public static FilenameFilter imageFilter;

    static {
        imageFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".jpeg");
            }
        };
    }
}
