package zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @author lywu
 * @create 2018-02-05
 */

public class ZooKeeper_Constructor_Usage_Simple implements Watcher
{
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception
    {
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 5000, new ZooKeeper_Constructor_Usage_Simple());
        System.out.println(zooKeeper.getState());
        try {
            connectedSemaphore.await();
        } catch (InterruptedException e) {}

        System.out.println("ZooKeeper session established");
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event)
    {
        System.out.println("Received watched event: " + event);
        if (Event.KeeperState.SyncConnected == event.getState())
        {
            connectedSemaphore.countDown();
        }
    }
}
