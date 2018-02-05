package zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author lywu
 * @create 2018-02-05
 */

public class ZooKeeper_SetData_API_ASync_Usage implements Watcher
{
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;

    public static void main(String[] args) throws Exception
    {
        String path = "/zk-book";
        zk = new ZooKeeper("localhost:2181", 5000, new ZooKeeper_SetData_API_ASync_Usage());
        connectedSemaphore.await();

        zk.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.setData(path, "456".getBytes(), -1, new IStatCallback(), null);

        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event)
    {
        if (Event.KeeperState.SyncConnected == event.getState())
        {
            if (Event.EventType.None == event.getType() && null == event.getPath())
            {
                connectedSemaphore.countDown();
            }
        }
    }
}

class IStatCallback implements AsyncCallback.StatCallback
{
    public void processResult(int rc, String path, Object ctx, Stat stat)
    {
        if (rc == 0)
        System.out.println("success");
    }
}
