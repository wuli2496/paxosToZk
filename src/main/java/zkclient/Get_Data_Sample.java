package zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author lywu
 * @create 2018-02-07
 */

public class Get_Data_Sample
{
    private static final String SERVER = "localhost:2181";

    public static void main(String[] args) throws Exception
    {
        String path = "/zk-book";

        ZkClient zkClient = new ZkClient(SERVER, 5000);
        zkClient.createEphemeral(path, "123");
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("path: " + s + " has changed, new data: " + o);
            }

            public void handleDataDeleted(String s) throws Exception {
                System.out.println("path: " + s + " has deleted");
            }
        });

        System.out.println(zkClient.readData(path));
        zkClient.writeData(path, "456");
        Thread.sleep(1000);
        zkClient.delete(path);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
