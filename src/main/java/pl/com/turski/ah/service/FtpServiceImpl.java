package pl.com.turski.ah.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.turski.ah.core.setting.Setting;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * User: Adam
 */
@Service
public class FtpServiceImpl implements FtpService {

    @Autowired
    private Setting setting;

    @Override
    public boolean testConnection() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void upload(List<BufferedImage> images) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
