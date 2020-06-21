package gc.garcol.nalsolution.cache.repository;

import gc.garcol.nalsolution.entity.Account;
import gc.garcol.nalsolution.lock.smartlock.SmartLockPool;

import java.util.concurrent.Callable;

/**
 * Note: fatherLock must wrap the childLock.
 * @author thai-van
 **/
public enum CacheSmartLock {

    C_ACCOUNT_EMAIL_LOCK(Account.class)
    ;

    Class<?> clazz;

    CacheSmartLock(Class<?> clazz) {
        this.clazz = clazz;
    }

    public static CacheSmartLock getByClass(Class<?> clazz) {
        CacheSmartLock[] locks = CacheSmartLock.values();
        for (CacheSmartLock lock : locks) {
            if (lock.clazz.equals(clazz)) return lock;
        }

        throw new IllegalArgumentException("CacheSmartLock -> getByClass. " + clazz.getName() + ". Not found");
    }

    SmartLockPool smartLockPool = new SmartLockPool();

    public <ID extends Comparable, T> T callInLock(ID id, Callable<T> callable) {
        return smartLockPool.callInLock(id, clazz, callable);
    }

    public <ID extends Comparable> void runInLock(ID id, Runnable runnable) {
        smartLockPool.runInLock(id, clazz, runnable);
    }
}
