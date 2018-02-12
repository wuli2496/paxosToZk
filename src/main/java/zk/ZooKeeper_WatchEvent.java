package zk;

import org.apache.zookeeper.*;

public class ZooKeeper_WatchEvent implements Watcher
{
    private static final String SERVER = "localhost:2181";
    private static final String PATH = "/zk-book";

    public static void main(String[] args) throws Exception
    {
        ZooKeeper zkClient = new ZooKeeper(SERVER, 3000, new ZooKeeper_WatchEvent());
        zkClient.addAuthInfo("digest", "taokeeper:true".getBytes());
        zkClient.create(PATH, "".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);


        ZooKeeper zkClient_error = new ZooKeeper(SERVER, 3000, new ZooKeeper_WatchEvent());
        zkClient_error.addAuthInfo("digest2", "taokeeper:error".getBytes());
        zkClient_error.getData(PATH, true, null);

        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event)
    {
        System.out.println("keeper state: " + event.getState());
    }
}
