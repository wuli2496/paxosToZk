package curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Create_Session_Sample_fluent
{
    private static final String SERVER = "localhost:2181";

    public static void main(String[] args) throws Exception
    {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(SERVER).sessionTimeoutMs(5000).retryPolicy(retryPolicy).build();
        client.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
