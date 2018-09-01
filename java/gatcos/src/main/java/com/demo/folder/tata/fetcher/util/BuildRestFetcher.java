package com.demo.folder.tata.fetcher.util;

import com.demo.folder.tata.config.mfi.UrlResolver;
import com.demo.folder.tata.fetcher.UserRestFetcher;
import com.demo.folder.tata.fetcher.data.UserData;
import com.demo.folder.tata.fetcher.params.RestParam;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.demo.folder.tata.config.AppConfig.APPCONFIG;
import static java.util.stream.Collectors.toList;

public class BuildRestFetcher {

    public static UserData getUsers() {

        UrlResolver ep = APPCONFIG.getRestEndPoint().getUserRestServices();
        return ((UserData) new UserRestFetcher(buildRestParam(ep,
                ImmutableMap.of())).getData());

    }


    public static <T> CompletableFuture<List<T>> extractFuture(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture =
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        return allDoneFuture.thenApply(v ->
                futures.stream().
                        map(future -> future.join()).
                        collect(toList())
        );
    }

    private static RestParam buildRestParam(UrlResolver ep, Map<String, String> queryParams) {
        return new RestParam().setUri(ep.getUri()).setHttpMethod(ep.getType()).setQueryParams(queryParams);
    }
}
