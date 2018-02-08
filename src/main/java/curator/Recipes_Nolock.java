package curator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @author lywu
 * @create 2018-02-08
 */

public class Recipes_Nolock
{
    public static void main(String[] args) throws Exception
    {
        final CountDownLatch down = new CountDownLatch(1);

        for (int i = 0; i < 10; i++)
        {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        down.await();
                    } catch (Exception e) {

                    }
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss|SSS");
                    String orderNo = simpleDateFormat.format(new Date());
                    System.out.println("生成的订单号: " + orderNo);
                }
            }).start();
        }

        down.countDown();
    }
}
