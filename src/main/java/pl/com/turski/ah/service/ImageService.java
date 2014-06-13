package pl.com.turski.ah.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * User: Adam
 */
public interface ImageService {

    public void cleanDirectory(File galleryDirectory) throws IOException;

    public void processImage(File galleryDirectory, File image) throws IOException;

    public void createGallery(File galleryDirectory, File image, File previousImage, File nextImage) throws IOException;

    public BufferedImage resizeImage(BufferedImage image, Integer width);
}
