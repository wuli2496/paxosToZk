package zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author lywu
 * @create 2018-02-05
 */

public class ZooKeeper_GetData_API_ASync_Usage implements Watcher
{
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws Exception
    {
        String path = "/zk-book";
        zk = new ZooKeeper("localhost:2181", 5000, new ZooKeeper_GetData_API_ASync_Usage());
        connectedSemaphore.await();

        zk.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        zk.getData(path, true, new IDataCallback(), stat);

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
                    zk.getData(event.getPath(), true, new IDataCallback(), null);
                } catch (Exception e) {}
            }
        }
    }
}

class IDataCallback implements AsyncCallback.DataCallback
{
    public void processResult(int rc, String path, Object ctx, byte data[],
                              Stat stat)
    {
        System.out.println("rc: " + rc + ", path: " + path + ", data: " + new String(data) + ", czxid: " + stat.getCzxid() + ", mzxid: " + stat.getMzxid() + ", version: " + stat.getVersion());
    }
}
