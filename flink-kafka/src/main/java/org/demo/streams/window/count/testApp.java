package org.demo.streams.window.count;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class testApp {

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        ExecutorService srv = Executors.newFixedThreadPool(5);

        Future f = srv.submit(()-> {

            try {
                Thread.sleep(5000);
                throw new RuntimeException("aaaa");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("");

        } );

        f.get();


    }


}
