package org.hello;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ThreadsApp {

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync( () -> {
            try {
                Thread.sleep(3000);
                System.out.println("3000 finished");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 3;
        });

        CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync( () -> {
            try {
                Thread.sleep(2000);
                System.out.println("2000 finished");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 5;
        });

        CompletableFuture<Integer> res = cf1.thenCombine(cf2, (c1,c2) -> c1+c2);

        System.out.println(res.get());


    }
}
