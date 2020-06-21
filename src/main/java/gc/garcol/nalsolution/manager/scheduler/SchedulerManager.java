package gc.garcol.nalsolution.manager.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author thai-van
 **/
public class SchedulerManager implements ISchedulerManager {

    private ScheduledExecutorService scheduler;

    public SchedulerManager(int coreSize) {
        scheduler = Executors.newScheduledThreadPool(coreSize);
    }

    public ScheduledFuture scheduleAtFixRate(Runnable command, long period) {
        return scheduler.scheduleAtFixedRate(command, 0, period, TimeUnit.MILLISECONDS);
    }

    public void cancel(ScheduledFuture scheduledFuture) {
        scheduledFuture.cancel(false);
    }
}
