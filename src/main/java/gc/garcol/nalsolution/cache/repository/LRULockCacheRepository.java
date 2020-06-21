package gc.garcol.nalsolution.cache.repository;

import gc.garcol.nalsolution.manager.scheduler.SchedulerManager;

import java.util.concurrent.Callable;

/**
 * @author thai-van
 **/
public abstract class LRULockCacheRepository<ID extends Comparable, T> extends LRUCacheRepository<ID, T> {

    public LRULockCacheRepository(SchedulerManager schedulerManager, CacheSmartLock cacheSmartLock) {
        super(schedulerManager);
        this.cacheSmartLock = cacheSmartLock;
    }

    protected final CacheSmartLock cacheSmartLock;

    public <V> V callInLock(ID id, Callable<V> callable) {
        return cacheSmartLock.callInLock(id, callable);
    }

    public void runInLock(ID id, Runnable runnable) {
        cacheSmartLock.runInLock(id, runnable);
    }

}
