package pl.com.turski.ah.service;

import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.turski.ah.core.setting.SettingManager;
import pl.com.turski.ah.model.setting.GallerySetting;
import pl.com.turski.ah.model.setting.Setting;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * User: Adam
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private SettingManager settingManager;

    @Override
    public boolean generateGallery(File galleryDirectory, List<File> images) throws IOException {
        if (galleryDirectory == null) {
            throw new IllegalArgumentException("Invalid method argument: galleryDirectory==null");
        }
        if (images == null) {
            throw new IllegalArgumentException("Invalid method argument: images==null");
        }

        FileUtils.cleanDirectory(galleryDirectory);

        Setting setting = settingManager.getSetting();
        GallerySetting gallerySetting = setting.getGallerySetting();

        for (File image : images) {
            BufferedImage buffImage = ImageIO.read(image);
            BufferedImage resizedBuffImage = resizeImage(buffImage, gallerySetting.getImageWidth());
            ImageIO.write(resizedBuffImage, "jpg", new File(galleryDirectory, image.getName() + "_new"));
            BufferedImage thumbnailBuffImage = resizeImage(buffImage, gallerySetting.getThumbnailWidth());
            thumbnailBuffImage.getHeight();
            ImageIO.write(thumbnailBuffImage, "jpg", new File(galleryDirectory, image.getName() + "_thumb"));
        }

        return true;
    }

    @Override
    public BufferedImage resizeImage(BufferedImage image, Integer width) {
        return Scalr.resize(image, Scalr.Mode.FIT_TO_WIDTH, width);
    }
}
