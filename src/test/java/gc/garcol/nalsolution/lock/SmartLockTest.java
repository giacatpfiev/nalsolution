package gc.garcol.nalsolution.lock;

import gc.garcol.nalsolution.lock.smartlock.SmartLockPool;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.IntStream;

/**
 * @author thai-van
 **/
public class SmartLockTest {

    @Test
    public void testLock() {
        SmartLockPool lockPool = new SmartLockPool();

        int[] data = new int[] {0, 0};

        int nLoop = 100_000;

        int nThread_0 = 5;
        Long id_0 = 0L;
        Thread[] thread_0 = new Thread[nThread_0];

        IntStream.range(0, nThread_0).forEach(i -> {
            thread_0[i] = new Thread(() -> {
                sleep(1_000);
                lockPool.runInLock(id_0, SmartLockTest.class, () -> {
                    IntStream.range(0, nLoop).forEach(j -> {
                        data[0] += 1;
                    });

                    sleep(10);
                });
            });
        });

        int nThread_1 = 10;
        Long id_1 = 1L;
        Thread[] thread_1 = new Thread[nThread_1];

        IntStream.range(0, nThread_1).forEach(i -> {
            thread_1[i] = new Thread(() -> {
                sleep(1_000);
                lockPool.runInLock(id_1, SmartLockTest.class, () -> {
                    IntStream.range(0, nLoop).forEach(j -> {
                        data[1] += 1;
                    });

                    sleep(100);
                });
            });
        });

        IntStream.range(0, nThread_0).forEach(i -> {
            thread_0[i].start();
        });

        IntStream.range(0, nThread_1).forEach(i -> {
            thread_1[i].start();
        });

        IntStream.range(0, nThread_0).forEach(i -> {
            try {
                thread_0[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        IntStream.range(0, nThread_1).forEach(i -> {
            try {
                thread_1[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Assert.assertEquals(nLoop * nThread_0, data[0]);
        Assert.assertEquals(nLoop * nThread_1, data[1]);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
