package zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author lywu
 * @create 2018-02-05
 */

public class ZooKeeper_GetData_API_Sync_Usage implements Watcher
{
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws Exception
    {
        zk = new ZooKeeper("localhost:2181", 5000, new ZooKeeper_GetData_API_Sync_Usage());
        connectedSemaphore.await();

        String path = "/zk-book";
        zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(new String(zk.getData(path, true, stat)));
        System.out.println("czxid: " + stat.getCzxid() + ", mzxid: " + stat.getMzxid() + ", version: " + stat.getVersion());

        zk.setData(path, "123".getBytes(), -1);

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
            else if (Event.EventType.NodeDataChanged == event.getType())
            {
                try {
                    System.out.println(new String(zk.getData(event.getPath(), true, stat)));
                    System.out.println("czxid: " + stat.getCzxid() + ", mzxid: " + stat.getMzxid() + ", version: " + stat.getVersion());
                } catch (Exception e) {}
            }
        }
    }
}
