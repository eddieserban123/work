package com.demo.folder.tata.fetcher.main;

import com.demo.folder.tata.fetcher.data.UserData;
import com.demo.folder.tata.fetcher.util.BuildRestFetcher;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import static com.demo.folder.tata.fetcher.util.BuildRestFetcher.*;
import static java.util.stream.Collectors.toList;

public class AppMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //read series name from config list


        ExecutorService executor = Executors.newFixedThreadPool(6); //TODO should be configurable

        //1 RETREIVING USERS
        CompletableFuture<UserData> users =
                CompletableFuture.supplyAsync(() -> BuildRestFetcher.getUsers(), executor);
        UserData data = users.get();
        data.getData().stream().forEach(System.out::println);



    }

    private static List<String> retrieveBBCodes(List<String> mfiSeriesName) {
        //TODO  for the momment leave it as it is in the current GAATCOSt
        return mfiSeriesName.stream().map(mfi -> mfi.replace('_', ' ')).collect(toList());
    }


}
