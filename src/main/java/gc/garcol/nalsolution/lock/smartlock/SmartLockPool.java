package gc.garcol.nalsolution.lock.smartlock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 * @author thai-van
 **/
@Slf4j
public class SmartLockPool {

    private final ConcurrentMap<String, SmartLock> lockPool = new ConcurrentHashMap<>();

    private final String SEPARATE = ".";

    /**
     * Run a functional interface in a suitable lock
     * @param id
     * @param clazz
     * @param callable
     * @param <T>
     * @return
     */
    public <ID extends Comparable, T> T callInLock(ID id, Class<?> clazz, Callable<T> callable) {

        Assert.notNull(id, "try to call in LOCK with null Id");
        Assert.notNull(clazz, "try to call in LOCK null clazz with id " + id);
        Assert.notNull(id, "try to call in LOCK with null callable with id " + id + " and class " + clazz.getName());

        String lockKey = generateLock(id, clazz);
        SmartLock lock = getLock(lockKey);
        T result = null;
        synchronized (lock) {
            try {
                result = callable.call();
            } catch (Exception e) {
                log.error("{}", e);
            } finally {
                unlock(lockKey, lock);
            }
        }

        return result;
    }

    /**
     * Run a functional interface in a suitable lock
     * @param id
     * @param clazz
     * @param runnable
     * @param <ID>
     */
    public <ID extends Comparable> void runInLock(ID id, Class<?> clazz, Runnable runnable) {

        Assert.notNull(id, "try to run in LOCK with null Id");
        Assert.notNull(clazz, "try to run in LOCK null clazz with id " + id);
        Assert.notNull(id, "try to run in LOCK with null runnable with id " + id + " and class " + clazz.getName());

        String lockKey = generateLock(id, clazz);
        SmartLock lock = getLock(lockKey);
        synchronized (lock) {
            try {
                runnable.run();
            } finally {
                unlock(lockKey, lock);
            }
        }
    }

    /**
     * If has no lock, then create a new lock and push into lockPool.
     * Else get lock from lockPool.
     * In the end, increase number of thread trying to acquire this lock by ONE.
     * @param key
     * @return instance of SmartLock
     */
    private SmartLock getLock(@NotNull String key) {

        Assert.notNull(key, "try to get lock with NUll key");

        SmartLock lock = lockPool.get(key);

        if (lock == null) {
            synchronized (this) {
                lock = lockPool.get(key);
                if (lock == null) {
                    lock = new SmartLock();
                    lockPool.put(key, lock);
                    log.info("create new lock {}", key);
                }
            }
        }

        synchronized(lock) {
            lock.lockedCount++;
        }

        return lock;
    }

    private <ID extends Comparable, T> String generateLock(ID id, Class<T> clazz) {
        return clazz.getName() + SEPARATE + id;
    }

    /**
     * Try to unlock, if there is no thread that tries to acquire this lock, then the lock will be removed from pool.
     * <h2>DO NOT CALL into other method except {@link SmartLockPool#callInLock(Comparable, Class, Callable)}
     * or {@link SmartLockPool#runInLock(Comparable, Class, Runnable)}</h2>
     * Only be called into synchronized block code of {@link SmartLockPool#callInLock(Comparable, Class, Callable)}
     * or {@link SmartLockPool#runInLock(Comparable, Class, Runnable)}</h2>
     * .
     *
     * @param key
     * @param lock
     */
    private void unlock(String key, SmartLock lock) {
        Assert.notNull(key, "try to unlock null key");
        Assert.notNull(lock, "try to unlock null lock with key " + key);

        lock.lockedCount--;
        if (lock.lockedCount == 0) {
            lockPool.remove(key);
            log.info("remove lock {}", key);
        }
    }
}
