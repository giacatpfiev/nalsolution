package gc.garcol.nalsolution.utils;

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

}
