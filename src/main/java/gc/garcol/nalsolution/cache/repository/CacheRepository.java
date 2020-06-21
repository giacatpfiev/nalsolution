package gc.garcol.nalsolution.cache.repository;

/**
 * @author thai-van
 **/
public interface CacheRepository<ID, V> {

    V findById(ID id);

    /**
     * Save or update a pair(key, value).
     * <br>* Create if there is no key in cache.
     * <br>* Otherwise replace the old value with the new one.
     * x
     * @param id
     * @param value
     */
    void save(ID id, V value);

    V delete(ID id);
}
