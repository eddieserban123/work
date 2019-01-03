package concurrencies;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppMainCountDownLatch {

    public static void main(String[] args) throws IOException {


        final CountDownLatch ct = new CountDownLatch(3);

        Runnable judge = () -> {
            try {
                Thread.sleep(3000 + new Random().nextInt(3000));
                 ct.countDown();
//                 ct.countDown();
//                 ct.countDown();
                System.out.println("judge pass !!!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };


        Runnable concurrent = () -> {
            try {
                ct.await();
                System.out.println(" finally concurrent recevies 3 notes  !");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        new Thread(concurrent).start();

        Runnable concurrent1 = () -> {
            try {
                ct.await();
                System.out.println(" finally concurrent recevies 3 notes  !");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        new Thread(concurrent1).start();
        ExecutorService serv = Executors.newFixedThreadPool(3);

        for(int i =0;i<3;i++) {
            char ch;
            do {
                ch = (char)System.in.read();
            } while(ch!='q');
            serv.submit(judge);
        }


    }
}
