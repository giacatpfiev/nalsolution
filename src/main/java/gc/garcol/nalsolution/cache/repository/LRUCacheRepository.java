package gc.garcol.nalsolution.cache.repository;

import gc.garcol.nalsolution.cache.collection.CacheLinkedList;
import gc.garcol.nalsolution.cache.collection.Node;
import gc.garcol.nalsolution.manager.scheduler.SchedulerManager;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledFuture;

import static java.lang.System.currentTimeMillis;

/**
 * <h3>Simple LRU cache with TTL.</h3>
 * Node's TTL will be refresh when using {@link this#findById(Object)} or {@link this#save(Object, Object)}.
 * <br> NOTE: all time values have a MILLISECOND format.
 * @author thai-van
 **/
@Slf4j
public abstract class LRUCacheRepository<ID, V> implements CacheRepository<ID, V> {

    protected final static int DEFAULT_SIZE = 50_000;
    protected final static int DEFAULT_SCAN_PERIOD_IN_MILLIS = 10_000;
    protected final static int DEFAULT_TIME_TO_LIVE = 1_200_000;

    protected final ConcurrentMap<ID, Node<ID, V>> index = new ConcurrentHashMap<>();

    protected final CacheLinkedList<ID, V> cache = new CacheLinkedList<>();

    protected final Object LOCK = new Object();

    protected int size;

    protected SchedulerManager schedulerManager;

    protected ScheduledFuture scheduledFuture;

    public LRUCacheRepository(SchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }

    /**
     * Remove expired value.
     */
    protected void scheduleRemove() {
        log.info("SCHEDULE REMOVE AT: {}", getClass().getName());
        scheduledFuture = schedulerManager.scheduleAtFixRate(this::scanningExpiredNode, getScanPeriod());
    }

    protected void cancelSchedule() {
        scheduledFuture.cancel(false);
    }

    protected void scanningExpiredNode() {
        synchronized (LOCK) { // stop the world
            keySet().forEach(id -> {
                Node<ID, V> node = index.get(id);
                if (isExpired(node)) {
                    remove(id);
                    log.info("Schedule remove node {} - {}", getClass(), node);
                }
            });
        }
    }

    public boolean contains(ID id) {

        return index.containsKey(id);
    }

    /**
     * If node is existed and get expired, delete it from cache.
     * Else if not is existed then refresh the node's TTL.
     * @param id
     * @return existed node
     */
    @Override
    public V findById(ID id) {

        Node<ID, V> node = index.get(id);

        if (Objects.isNull(node)) return null;

        synchronized (LOCK) {

            if (isExpired(node)) {
                remove(id);
                return null;
            }

            cache.remove(node);
            cache.addFirst(node);
            updateTTL(node);
        }

        return node.value();
    }

    @Override
    public void save(ID id, V value) {
        synchronized (LOCK) {
            if (index.containsKey(id)) {
                Node<ID, V> node = updateWhenPresent(id, value);
                updateTTL(node);
                return;
            }

            Node<ID, V> addedNode = addWhenAbsent(id, value);
            updateTTL(addedNode);
        }
    }

    // warning: duplicated code, need to refactor.
    public void save(ID id, V value, long timeToLive) {
        synchronized (LOCK) {
            if (index.containsKey(id)) {
                Node<ID, V> node = updateWhenPresent(id, value);
                updateTTL(node, timeToLive);
                return;
            }

            Node<ID, V> addedNode = addWhenAbsent(id, value);
            updateTTL(addedNode, timeToLive);
        }
    }

    protected Node<ID, V> updateWhenPresent(ID id, V value) {
        Node<ID, V> node = index.get(id);
        node.value(value);
        cache.remove(node);
        cache.addFirst(node);
        return node;
    }

    protected Node<ID, V> addWhenAbsent(ID id, V value) {
        Node<ID, V> addedNode = cache.addFirst(id, value);
        index.put(id, addedNode);
        size++;
        ensureCapacityInternal();
        return addedNode;
    }

    protected void ensureCapacityInternal() {
        if (size > maxSize()) {
            Node<ID, V> tail = cache.removeLast();
            index.remove(tail.id());
            size--;
        }
    }

    @Override
    public V delete(ID id) {

        if (!index.containsKey(id)) return null;

        log.info("{} remove key {}", getClass().getName(), id);

        return remove(id);
    }

    protected V remove(ID id) {
        Node<ID, V> node;
        synchronized (LOCK) {
            node = index.remove(id);
            if (Objects.isNull(node)) return null;

            cache.remove(node);
            size--;
        }
        return node.value();
    }

    /**
     * Refresh TTL for node which has been used.
     * @param node
     */
    protected void updateTTL(Node<ID, V> node) {
        node.setExpiredTime(currentTimeMillis() + getTimeToLive());
    }

    public void updateTTL(Node<ID, V> node, long period) {
        node.setExpiredTime(currentTimeMillis() + period);
    }

    protected boolean isExpired(Node<ID, V> node) {
        return node.getExpiredTime() < currentTimeMillis();
    }

    public Set<ID> keySet() {
        return index.keySet();
    }

    /**
     * Child maybe override this function to get more cache capacity
     */
    public int maxSize() {
        return DEFAULT_SIZE;
    }

    /**
     * Child maybe override this function to get more performance
     */
    public int getScanPeriod() {
        return DEFAULT_SCAN_PERIOD_IN_MILLIS;
    }

    /**
     * Child maybe override this function to have a suitable TTL
     */
    public int getTimeToLive() {
        return DEFAULT_TIME_TO_LIVE;
    }

    public int getSize() {
        return size;
    }
}
