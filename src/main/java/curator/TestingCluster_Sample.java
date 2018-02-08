package curator;

import org.apache.curator.test.TestingCluster;
import org.apache.curator.test.TestingZooKeeperServer;


/**
 * @author lywu
 * @create 2018-02-08
 */

public class TestingCluster_Sample
{
    public static void main(String[] args) throws Exception
    {
        TestingCluster cluster = new TestingCluster(3);
        cluster.start();
        Thread.sleep(2000);

        TestingZooKeeperServer leader = null;
        for (TestingZooKeeperServer zs : cluster.getServers())
        {
            System.out.println(zs.getInstanceSpec().getServerId() + "-");
            System.out.println(zs.getQuorumPeer().getServerState() + "-");
            System.out.println(zs.getInstanceSpec().getDataDirectory().getAbsolutePath());
            if (zs.getQuorumPeer().getServerState().equals("leading"))
            {
                leader = zs;
            }
        }

        leader.kill();
        System.out.println("--After leader kill");
        for (TestingZooKeeperServer zs : cluster.getServers())
        {
            System.out.println(zs.getInstanceSpec().getServerId() + "-");
            System.out.println(zs.getQuorumPeer().getServerState() + "-");
            System.out.println(zs.getInstanceSpec().getDataDirectory().getAbsolutePath());
        }

        cluster.stop();
    }
}
