package mypck;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

import static mypck.UserService.getUser;

public class AppMain2 {

    public static void main(String[] args) {
       Integer userId = 1;



//        CompletableFuture.supplyAsync(() -> getUser(userId))
//                .thenApply(CreditRatingService::getCreditRatingSystem1)
//                .thenAccept(System.out::println);


        CompletableFuture<User> user = CompletableFuture.supplyAsync(() -> getUser(userId));

        CompletableFuture<Integer> rating1 =
                user.thenApplyAsync(CreditRatingService::getCreditRatingSystem1);
        CompletableFuture<Integer> rating2 =
                user.thenApplyAsync(CreditRatingService::getCreditRatingSystem2);

        rating1
                .thenCombineAsync(rating2, Integer::sum)
                .thenAccept(System.out::println);



    }

}

class User {


    private Integer value;
    private Integer age;
    private Integer id;

    public User(Integer value, Integer age, Integer id) {
        this.value = value;
        this.age = age;
        this.id = id;
    }


    public Integer getId() {
        return id;
    }
}

class UserService {

    static User getUser(int userId) {
        int val = ThreadLocalRandom.current().nextInt(1, 10);
        try {
            Thread.sleep(val*500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("User retrieval finish !!!");
        return new User(2 + val, 20 + val, userId);
    }
}

class CreditRatingService {
    static ThreadLocal<Integer> val = new ThreadLocal<>();

    public static Integer getCreditRatingSystem1(User userId) {
        int score = ThreadLocalRandom.current().nextInt(1, 10);
        doJob("creditRatingSystem1 for " + userId.getId(), userId, score);
        return score;
    }

    public static Integer getCreditRatingSystem2(User userId) {
        int score = ThreadLocalRandom.current().nextInt(1, 10);
        doJob("creditRatingSystem2 for " + userId.getId(), userId, score);
        return score;
    }


    private static int getAValue() {
        return val.get();
    }


    private static void doJob(String name, User userId, int score) {

        try {
            synchronized (CreditRatingService.class) {
                System.out.println(" task " + name + " start on threadId " + Thread.currentThread().getId());
                val.set(ThreadLocalRandom.current().nextInt(10000, 12000));

            }
            Thread.sleep(val.get());
            synchronized (CreditRatingService.class) {
                System.out.println(" task " + name + " finished in " + val.get().toString() + " ms and obtained for user " + userId.getId() + " score " + score ) ;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
