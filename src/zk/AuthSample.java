package zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class AuthSample
{
    final static String PATH = "/zk-book-auth_test";

    public static void main(String[] args) throws Exception
    {

        ZooKeeper zooKeeper1 = new ZooKeeper("localhost:2181", 50000, null);
        zooKeeper1.addAuthInfo("digest", "foo:true".getBytes());
        zooKeeper1.create(PATH, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

        ZooKeeper zooKeeper2 = new ZooKeeper("localhost:2181", 50000, null);
        zooKeeper2.getData(PATH, false, null);

        //Thread.sleep(Integer.MAX_VALUE);

    }
}
