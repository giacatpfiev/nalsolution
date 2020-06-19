package gc.garcol.nalsolution.utils.codec;

/**
 * @author thai-van
 **/
public enum JsonUtil {

    JSON;

    public <T> String toJson(T object) {
        return JacksonUtil.toJson(object);
    }

    public <T> String toBeautyJson(T object) {
        return GsonUtil.PRETTY_GSON.toJson(object);
    }

    public <T> T fromJson(String value, Class<T> clazz) {
        return GsonUtil.GSON.fromJson(value, clazz);
    }

}
