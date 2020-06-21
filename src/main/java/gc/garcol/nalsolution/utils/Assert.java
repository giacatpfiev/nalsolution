package gc.garcol.nalsolution.utils;

import java.util.Objects;
import java.util.Optional;

/**
 * @author thai-van
 **/
public enum Assert {

    ASSERT;

    public <T extends Number> boolean isZero(T number) {
        return number.equals(0);
    }

    public <T extends Number> boolean nonZero(T number) {
        return !number.equals(0);
    }

    public <T> boolean cacheHit(T obj) {
        return Objects.nonNull(obj);
    }

    public <T> boolean cacheMiss(T obj) {
        return Objects.isNull(obj);
    }

    public <T> boolean databaseHit(Optional<T> opt) {
        return opt.isPresent();
    }

    public <T> boolean databaseMiss(Optional<T> opt) {
        return !opt.isPresent();
    }

}
