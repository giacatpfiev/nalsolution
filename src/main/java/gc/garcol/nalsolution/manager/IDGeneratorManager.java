package gc.garcol.nalsolution.manager;

import gc.garcol.nalsolution.exception.ServerErrorException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author thai-van
 **/
@Slf4j
public class IDGeneratorManager {

    private Map<Class, AtomicLong> IDs = new HashMap<>();

    /**
     *
     * @param clazz
     * @return next ID value related to clazz
     */
    public Long increaseAndGet(Class<?> clazz) {

        if (!IDs.containsKey(clazz)) throw new ServerErrorException("IDGeneratorManager -> increaseAndGet. Not found: " + clazz);
        AtomicLong currentID = IDs.get(clazz);
        return currentID.incrementAndGet();

    }

    /**
     * Using reflect to register
     * @param clazz
     * @param currentValue
     */
    private void registerGenerator(Class<?> clazz, Long currentValue) {

        Assert.notNull(clazz, "IDGeneratorManager -> registerGenerator. Register null class");
        Assert.notNull(currentValue, "IDGeneratorManager -> registerGenerator. Register null value for class " + clazz);

        AtomicLong atomicLong = new AtomicLong(currentValue);
        IDs.put(clazz, atomicLong);
        log.info("IDGeneratorManager -> registerGenerator. Clazz: {}, value: {}", clazz, currentValue);

    }

    /**
     *
     * @return
     */
    @SneakyThrows
    public Method getRegisterMethod() {
        
        String registerMethodName = "registerGenerator";
        Method method = getClass().getDeclaredMethod(registerMethodName, Class.class, Long.class);
        return method;

    }

}
