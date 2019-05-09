package org.demo.webserver.http;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.net.http.HttpClient.*;
import static java.util.AbstractMap.SimpleImmutableEntry;
import static java.util.stream.Collectors.toList;

public class App021HttpClientAsyncSearch {

    public static void main(String[] args) throws  InterruptedException, ExecutionException {

        List<URI> uris = List.of(
                "https://en.wikipedia.org/wiki/Albert_Einstein",
                "https://en.wikipedia.org/wiki/Adrian_Minune")
                .stream().map(URI::create).collect(toList());

        HttpClient client = getHttpClient();

        CompletableFuture<List<SimpleImmutableEntry>> res = sequence(searchOnSites(client, uris));
        res.get().stream().forEach(e-> System.out.println("uri : " + e.getKey() + " --> " + e.getValue()));

    }

    static<T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> com) {
        return CompletableFuture.allOf(com.toArray(new CompletableFuture<?>[com.size()]))
                .thenApply(v -> com.stream()
                        .map(CompletableFuture::join)
                        .collect(toList())
                );
    }



    private static List<CompletableFuture<SimpleImmutableEntry>> searchOnSites(HttpClient client, List<URI> uris) {

        return uris.stream().map(uri -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(uri)
                        .headers("Accept-Language", "*/*")
                        .build();
                return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body)
                        .thenApply(body -> countMatches(body, "genius"))
                        .thenApply(count -> new SimpleImmutableEntry(uri, count))
                        .exceptionally(__ -> new SimpleImmutableEntry(uri, -1));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).filter(e -> e != null).collect(Collectors.toList());


    }

    private static int countMatches(String text, String textToFind) {
        Matcher m = Pattern.compile(textToFind).matcher(text);
        int matches = 0;
        while (m.find())
            matches++;
        return matches;
    }


    private static HttpClient getHttpClient() {
        return newBuilder()
                .version(Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(5))
                .followRedirects(Redirect.ALWAYS)
                .build();
    }

}
