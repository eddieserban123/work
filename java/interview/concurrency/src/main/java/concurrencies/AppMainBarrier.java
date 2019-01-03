package concurrencies;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.*;

public class AppMainBarrier {

    public static void main(String[] args) throws IOException {


        final CyclicBarrier ct = new CyclicBarrier(3, () -> System.out.println("**** Party !"));

        Runnable judge = () -> {
            try {
                Thread.sleep(3000 + new Random().nextInt(3000));
                 ct.await();
                System.out.println("judge pass !!!");
            } catch (InterruptedException| BrokenBarrierException e) {
                e.printStackTrace();
            }
        };


        ExecutorService serv = Executors.newFixedThreadPool(3);

        for(int i =0;i<3;i++) {
            char ch;
            do {
                ch = (char)System.in.read();
            } while(ch!='q');
            serv.submit(judge);
        }

        char ch;
        do {
            ch = (char)System.in.read();
        } while(ch!='q');
    }
}
