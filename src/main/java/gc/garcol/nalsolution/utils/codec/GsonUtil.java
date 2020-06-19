package gc.garcol.nalsolution.utils.codec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author thai-van
 **/
public class GsonUtil {

    static final Gson GSON = new Gson();

    static final Gson PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();

}
