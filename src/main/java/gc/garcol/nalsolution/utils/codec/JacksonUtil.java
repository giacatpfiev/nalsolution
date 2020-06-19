package gc.garcol.nalsolution.utils.codec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author thai-van
 **/
@Slf4j
public class JacksonUtil {

    static ObjectMapper JSON = new ObjectMapper();

    static {
        JSON.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    static ObjectMapper XML = new XmlMapper();

    public static <T> String toJson(T object) {
        try {
            return JSON.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("[ERROR] :: JacksonUtil -> toJson: {} - {}", object == null ? null : object.getClass(), e.getOriginalMessage());
            return "";
        }
    }

    public static <T> T fromJson(String value, Class<T> clazz) {
        try {
            return JSON.readValue(value, clazz);
        } catch (JsonProcessingException e) {
            log.error("[ERROR] :: JacksonUtil -> fromJson: {} - {} - {}", value, clazz, e.getOriginalMessage());
            return null;
        }
    }

    public static <T> T fromXml(String value, Class<T> clazz) {
        try {
            return XML.readValue(value, clazz);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
