package pl.com.turski.ah.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.turski.ah.core.setting.SettingManager;
import pl.com.turski.ah.exception.CommonFileException;
import pl.com.turski.ah.model.core.TemplateAttr;
import pl.com.turski.ah.model.core.TemplateValue;
import pl.com.turski.ah.model.setting.GallerySetting;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * User: Adam
 */
@Service
public class CoreServiceImpl implements CoreService {

    private static final Logger LOG = LoggerFactory.getLogger(CoreServiceImpl.class);

    @Autowired
    private SettingManager settingManager;
    @Autowired
    private String template;
    @Autowired
    private String galleryPhotoTemplate;

    @Override
    public String fillTemplate(TemplateValue templateValue) {
        GallerySetting gallerySetting = settingManager.getGallerySetting();
        String filledTemplate = template;
        filledTemplate = filledTemplate.replace(TemplateAttr.TITLE.getKey(), templateValue.getTitle());
        filledTemplate = filledTemplate.replace(TemplateAttr.CAR_COLOR.getKey(), templateValue.getCarColor());
        filledTemplate = filledTemplate.replace(TemplateAttr.CAR_ENGINE.getKey(), templateValue.getCarEngine());
        filledTemplate = filledTemplate.replace(TemplateAttr.CAR_GEARBOX.getKey(), templateValue.getCarGearbox());
        filledTemplate = filledTemplate.replace(TemplateAttr.CAR_MILEAGE.getKey(), templateValue.getCarMileage());
        filledTemplate = filledTemplate.replace(TemplateAttr.CAR_POWER.getKey(), templateValue.getCarPower());
        filledTemplate = filledTemplate.replace(TemplateAttr.CAR_YEAR.getKey(), templateValue.getCarYear());
        filledTemplate = filledTemplate.replace(TemplateAttr.GALLERY_TITLE.getKey(), templateValue.getGalleryTitle());
        filledTemplate = filledTemplate.replace(TemplateAttr.RESOURCE_URL.getKey(), gallerySetting.getResourceUrl());
        filledTemplate = fillEquipment(templateValue, filledTemplate);
        filledTemplate = fillGallery(templateValue, filledTemplate, gallerySetting);
        return filledTemplate;
    }

    private String fillGallery(TemplateValue templateValue, String filledTemplate, GallerySetting gallerySetting) {
        File galleryDirectory = templateValue.getGalleryDirectory();
        List<File> thumbnailFiles = Arrays.asList(galleryDirectory.listFiles((FileFilter) new SuffixFileFilter(Arrays.asList("_thumb.jpg","_thumb.jpeg"))));
        StringBuilder galleryPhotos = new StringBuilder();
        for (File thumbnailFile : thumbnailFiles) {
            String filledPhotoTemplate = galleryPhotoTemplate;
            filledPhotoTemplate = filledPhotoTemplate.replace(TemplateAttr.IMAGE_URL.getKey(), gallerySetting.getGalleriesDirectory() + "/" + templateValue.getRemoteGalleryName() + "/" + thumbnailFile.getName());
            filledPhotoTemplate = filledPhotoTemplate.replace(TemplateAttr.IMAGE_HREF.getKey(), gallerySetting.getGalleriesDirectory() + "/" + templateValue.getRemoteGalleryName() + "/" + thumbnailFile.getName().replace("_thumb.jpg", ".html").replace("_thumb.jpeg", ".html"));
            galleryPhotos.append(filledPhotoTemplate);
        }
        filledTemplate = filledTemplate.replace(TemplateAttr.GALLERY_PHOTOS.getKey(), galleryPhotos);
        return filledTemplate;
    }

    private String fillEquipment(TemplateValue templateValue, String filledTemplate) {
        List<String> equipments = templateValue.getEquipments();
        StringBuilder equipmentTbody = new StringBuilder();
        for (int i = 0; i < equipments.size(); i++) {
            if (i % 2 == 0) {
                equipmentTbody.append("<tr>");
            }
            equipmentTbody.append("<td>").append(equipments.get(i)).append("</td>");
            if (i % 2 == 1) {
                equipmentTbody.append("</tr>");
            }
        }
        filledTemplate = filledTemplate.replace(TemplateAttr.EQUIPMENT_TBODY.getKey(), equipmentTbody);
        return filledTemplate;
    }

    @Override
    public URI createTemplateFile(String filledTemplate) throws CommonFileException {
        try {
            File filledTemplateFile = new File("preview.html");
            FileUtils.write(filledTemplateFile, "", Charset.forName("UTF-8"));
            FileUtils.write(filledTemplateFile,"<meta charset=\"utf-8\">",Charset.forName("UTF-8"),true);
            FileUtils.write(filledTemplateFile, filledTemplate, Charset.forName("UTF-8"),true);
            return filledTemplateFile.toURI();
        } catch (IOException e) {
            LOG.error("Nie udało się utworzyć pliku podglądu", e);
            throw new CommonFileException("Nie udało się utworzyć pliku podglądu.");
        }
    }
}
