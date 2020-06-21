package gc.garcol.nalsolution.manager.scheduler;

import java.util.concurrent.ScheduledFuture;

/**
 * @author thai-van
 **/
public interface ISchedulerManager {

    ScheduledFuture scheduleAtFixRate(Runnable command, long period);

}
