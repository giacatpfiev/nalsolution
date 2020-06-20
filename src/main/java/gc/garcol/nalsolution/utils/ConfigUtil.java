package gc.garcol.nalsolution.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author thai-van
 **/
public enum ConfigUtil {

    CONFIG_UTIL;

    public InputStream getInputStream(String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        return resource.getInputStream();
    }

}
