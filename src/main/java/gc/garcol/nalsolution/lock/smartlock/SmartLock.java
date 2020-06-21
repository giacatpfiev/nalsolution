package gc.garcol.nalsolution.lock.smartlock;

import static gc.garcol.nalsolution.utils.DateTimeUtil.DATE_TIME_UTIL;

/**
 * @author thai-van
 **/
public class SmartLock {

    /**
     * number of threads that try to acquire this
     */

    int lockedCount = 0;
    long createTime = DATE_TIME_UTIL.currentTimeMillis();

}
