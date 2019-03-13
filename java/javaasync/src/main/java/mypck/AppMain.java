package mypck;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class AppMain {

    public static void main(String[] args) {
        Job job = new Job();
        Integer val = CompletableFuture.
                supplyAsync(()-> job.doTaskA1()).
                thenCombineAsync(CompletableFuture.supplyAsync(()-> job.doTaskA2()), (s1,s2) -> s1 + s2
                ).
                join();

        System.out.println(val);

    }

}


class Job {

    public Integer doTaskA1(){
        doJob("A1");
        return getAnInt();
    }

    public Integer doTaskA2(){
        doJob("A2");
        return getAnInt();
    }

    public Integer doTaskB1(){
        doJob("A3");
        return getAnInt();
    }

    public Integer doTaskB2(){
        doJob("A4");
        return getAnInt();
    }

    private int getAnInt() {
        return ThreadLocalRandom.current().nextInt(1000, 2000);
    }


    private void doJob(String name) {

        try {
            synchronized(this) {
                System.out.println(" task " + name + " start");

            } int val = ThreadLocalRandom.current().nextInt(1000, 2000);
            Thread.sleep(val);
            synchronized(this) {
                System.out.println(" task " + name + " finished");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
