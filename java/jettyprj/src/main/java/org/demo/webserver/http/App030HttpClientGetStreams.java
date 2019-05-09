package org.demo.webserver.http;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Flow;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.net.http.HttpClient.*;
import static java.util.AbstractMap.SimpleImmutableEntry;
import static java.util.stream.Collectors.toList;

public class App030HttpClientGetStreams {

    public static void main(String[] args) throws Exception {

        URI uri = URI.create("https://demo.borland.com/testsite/stadyn_largepagewithimages.html");
        HttpClient client = getHttpClient();

        CompletableFuture res = searchOnSite(client, uri);
        System.in.read();
    }

    static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> com) {
        return CompletableFuture.allOf(com.toArray(new CompletableFuture<?>[com.size()]))
                .thenApply(v -> com.stream()
                        .map(CompletableFuture::join)
                        .collect(toList())
                );
    }


    private static CompletableFuture searchOnSite(HttpClient client, URI uri) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(uri)
                    .headers("Accept-Language", "*/*")
                    .build();
            Flow.Subscriber<String> stringFinder = new StringFinder("");
            return client.sendAsync(request,
                    HttpResponse.BodyHandlers.fromLineSubscriber(stringFinder));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

class StringFinder implements Flow.Subscriber<String> {

    private String term;
    private Flow.Subscription subscription;

    public StringFinder(String term) {
        this.term = term;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(String item) {
        System.out.println(item);
        subscription.request(1);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}