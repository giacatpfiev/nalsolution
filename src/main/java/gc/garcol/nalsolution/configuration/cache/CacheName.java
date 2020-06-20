package gc.garcol.nalsolution.configuration.cache;

/**
 * @author thai-van
 **/
public enum CacheName {
    ACCOUNT_ID("ACCOUNT_ID"),
    ACCOUNT_EMAIL("ACCOUNT_EMAIL"),
    WORK("WORK");

    String value;
    CacheName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
