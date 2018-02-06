package zkclient;

import org.I0Itec.zkclient.ZkClient;

import java.io.IOException;

public class Create_Session_Sample
{
    private static final String SERVER = "localhost:2181";

    public static void main(String[] args) throws IOException, InterruptedException
    {
        ZkClient zkClient = new ZkClient(SERVER, 5000);
        System.out.println("ZooKeeper session established");

    }
}
