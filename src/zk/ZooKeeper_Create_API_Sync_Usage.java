package zk;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * @author lywu
 * @create 2018-02-05
 */

public class ZooKeeper_Create_API_Sync_Usage implements Watcher
{
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception
    {
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 5000, new ZooKeeper_Create_API_Sync_Usage());
        connectedSemaphore.await();

        String path1 = zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Success create znode: " + path1);

        String path2 = zooKeeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("Success create znode: " + path2);



    }
    public void process(WatchedEvent event)
    {
        if (Event.KeeperState.SyncConnected == event.getState())
        {
            connectedSemaphore.countDown();
        }
    }
}
