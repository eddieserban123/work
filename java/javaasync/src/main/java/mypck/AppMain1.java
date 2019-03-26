package mypck;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class AppMain1 {

    public static void main(String[] args) {
        Job job = new Job();
        Integer val;
        CompletableFuture<Integer> f1,f2;

/*
  this way they execute  A1 and A2 in parallel(sum time t1)
                                                       combine t1+t2
                         B1 and B2 in parallel (sum time t2)

*/
        f1 = CompletableFuture.
                supplyAsync(()-> job.doTaskA1()).
                thenApplyAsync(v->
                        v + job.doTaskA2());


        f2 = CompletableFuture.
                supplyAsync(()-> job.doTaskB1()).
                thenApplyAsync(v->
                        v + job.doTaskB2());

        val = f2.thenCombineAsync(f1, (v1,v2) ->v1 + v2).join();



        System.out.println(val);

    }

}


class Job {
     ThreadLocal<Integer> val = new ThreadLocal<>();

    public Integer doTaskA1(){
        doJob("A1");
        return getAValue();
    }

    public Integer doTaskA2(){
        doJob("A2");
        return getAValue();
    }

    public Integer doTaskB1(){
        doJob("B1");
        return getAValue();
    }

    public Integer doTaskB2(){
        doJob("B2");
        return getAValue();
    }

    private int getAValue() {
        return val.get();
    }


    private void doJob(String name) {

        try {
            synchronized(this) {
                System.out.println(" task " + name + " start on threadId " + Thread.currentThread().getId());
                val.set(ThreadLocalRandom.current().nextInt(10000, 12000));

            }
            Thread.sleep(val.get());
            synchronized(this) {
                System.out.println(" task " + name + " finished in " + val.get().toString() + " ms");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
