package pl.com.turski.ah.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * User: Adam
 */
public interface ImageService {

    public boolean generateGallery(File galleryDirectory, List<File> images) throws IOException;

    public BufferedImage resizeImage(BufferedImage image, Integer width);
}
