package zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class AuthSample_Delete
{
    final static String PATH = "/zk-book-auth_test";
    final static String PATH2 = "/zk-book-auth_test/child";
    final static String SERVER = "localhost:2181";

    public static void main(String[] args) throws Exception
    {
        ZooKeeper zooKeeper1 = new ZooKeeper(SERVER, 5000, null);
        zooKeeper1.addAuthInfo("digest", "foo:true".getBytes());
        zooKeeper1.create(PATH, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        zooKeeper1.create(PATH2, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

        try {
            ZooKeeper zooKeeper2 = new ZooKeeper(SERVER, 5000, null);
            zooKeeper2.delete(PATH2, -1);
        } catch (Exception e) {
            System.out.println("删除节点失败: " + e.getMessage());
        }

        ZooKeeper zooKeeper3 = new ZooKeeper(SERVER, 5000, null);
        zooKeeper3.addAuthInfo("digest", "foo:true".getBytes());
        zooKeeper3.delete(PATH2, -1);
        System.out.println("成功删除节点: " + PATH2);

        ZooKeeper zooKeeper4 = new ZooKeeper(SERVER, 5000, null);
        zooKeeper4.delete(PATH, -1);
        System.out.println("成功删除结点: " + PATH);
    }
}
