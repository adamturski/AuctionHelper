package pl.com.turski.ah.service;

import pl.com.turski.ah.exception.CommonFileException;
import pl.com.turski.ah.model.core.TemplateValue;

import java.net.URI;

/**
 * User: Adam
 */
public interface CoreService {

    public String fillTemplate(TemplateValue templateValue) throws CommonFileException;

    public URI createTemplateFile(String s) throws CommonFileException;

}
