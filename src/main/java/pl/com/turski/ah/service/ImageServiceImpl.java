package pl.com.turski.ah.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.com.turski.ah.core.image.GalleryTemplateAttr;
import pl.com.turski.ah.core.setting.SettingManager;
import pl.com.turski.ah.model.setting.FtpSetting;
import pl.com.turski.ah.model.setting.GallerySetting;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * User: Adam
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private SettingManager settingManager;
    @Autowired
    private String galleryTemplate;
    @Autowired
    private Resource arrowLeftFile;
    @Autowired
    private Resource arrowRightFile;

    @Override
    public void cleanDirectory(File galleryDirectory) throws IOException {
        FileUtils.cleanDirectory(galleryDirectory);
        FileUtils.copyFileToDirectory(arrowLeftFile.getFile(), galleryDirectory);
        FileUtils.copyFileToDirectory(arrowRightFile.getFile(), galleryDirectory);
    }

    @Override
    public void processImage(File galleryDirectory, File image) throws IOException {
        GallerySetting gallerySetting = settingManager.getGallerySetting();
        BufferedImage buffImage = ImageIO.read(image);
        BufferedImage resizedBuffImage = resizeImage(buffImage, gallerySetting.getImageWidth());
        String newImageFileName = FilenameUtils.getBaseName(image.getName()) + "_." + FilenameUtils.getExtension(image.getName());
        ImageIO.write(resizedBuffImage, "jpg", new File(galleryDirectory, newImageFileName));
        BufferedImage thumbnailBuffImage = resizeImage(buffImage, gallerySetting.getThumbnailWidth());
        String newThumbnailFileName = FilenameUtils.getBaseName(image.getName()) + "_thumb." + FilenameUtils.getExtension(image.getName());
        ImageIO.write(thumbnailBuffImage, "jpg", new File(galleryDirectory, newThumbnailFileName));
    }

    @Override
    public void createGallery(File galleryDirectory, File image, File previousImage, File nextImage) throws IOException {
        GallerySetting gallerySetting = settingManager.getGallerySetting();
        FtpSetting ftpSetting = settingManager.getFtpSetting();
        String filledGalleryTemplate = galleryTemplate;
        filledGalleryTemplate = filledGalleryTemplate.replace(GalleryTemplateAttr.TITLE.getKey(), "Zdjęcie: " + FilenameUtils.getBaseName(image.getName()));
        filledGalleryTemplate = filledGalleryTemplate.replace(GalleryTemplateAttr.IMAGE_SRC.getKey(), FilenameUtils.getBaseName(image.getName()) + "_." + FilenameUtils.getExtension(image.getName()));
        filledGalleryTemplate = filledGalleryTemplate.replace(GalleryTemplateAttr.IMAGE_WIDTH.getKey(), "width:" + gallerySetting.getImageWidth() + "px;");
        if (previousImage == null) {
            filledGalleryTemplate = filledGalleryTemplate.replace(GalleryTemplateAttr.PREVIOUS_LINK_DISPLAY.getKey(), "display:none;");
            filledGalleryTemplate = filledGalleryTemplate.replace(GalleryTemplateAttr.PREVIOUS_LINK_HREF.getKey(), "");
        } else {
            filledGalleryTemplate = filledGalleryTemplate.replace(GalleryTemplateAttr.PREVIOUS_LINK_DISPLAY.getKey(), "");
            filledGalleryTemplate = filledGalleryTemplate.replace(GalleryTemplateAttr.PREVIOUS_LINK_HREF.getKey(), FilenameUtils.getBaseName(previousImage.getName()) + ".html");
        }
        if (nextImage == null) {
            filledGalleryTemplate = filledGalleryTemplate.replace(GalleryTemplateAttr.NEXT_LINK_DISPLAY.getKey(), "display:none;");
            filledGalleryTemplate = filledGalleryTemplate.replace(GalleryTemplateAttr.NEXT_LINK_HREF.getKey(), "");
        } else {
            filledGalleryTemplate = filledGalleryTemplate.replace(GalleryTemplateAttr.NEXT_LINK_DISPLAY.getKey(), "");
            filledGalleryTemplate = filledGalleryTemplate.replace(GalleryTemplateAttr.NEXT_LINK_HREF.getKey(), FilenameUtils.getBaseName(nextImage.getName()) + ".html");
        }
        String galleryFileName = FilenameUtils.getBaseName(image.getName()) + ".html";
        FileUtils.write(new File(galleryDirectory, galleryFileName), filledGalleryTemplate, Charset.forName("UTF-8"));
    }

    @Override
    public BufferedImage resizeImage(BufferedImage image, Integer width) {
        return Scalr.resize(image, Scalr.Mode.FIT_TO_WIDTH, width);
    }
}
