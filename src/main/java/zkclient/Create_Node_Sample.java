package zkclient;

import org.I0Itec.zkclient.ZkClient;

public class Create_Node_Sample
{
    private static final String SERVER = "localhost:2181";

    public static void main(String[] args) throws Exception
    {
        ZkClient zkClient = new ZkClient(SERVER, 5000);
        String path = "/zk-book/c1";
        zkClient.createPersistent(path, true);
    }
}
