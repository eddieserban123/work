package org.demo.webserver.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.net.http.HttpClient.*;

public class App020HttpClientAsync {

    public static void main(String[] args) throws IOException, InterruptedException {
        URI uri = URI.create("https://en.wikipedia.org/wiki/Albert_Einstein");
        HttpClient client = newBuilder()
                .version(Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(5))
                .followRedirects(Redirect.ALWAYS)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .headers("Accept-Language", "*/*")
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(body -> countMatches(body, "genius"))
                .exceptionally(__ -> -1)
                .thenAccept(no -> System.out.println("We've found for " + uri + " --> " + no)).join();

        //System.out.println(response.body());


    }


    private static int countMatches(String text, String textToFind) {
        Matcher m = Pattern.compile(textToFind).matcher(text);
        int matches = 0;
        while (m.find())
            matches++;
        return matches;
    }
}
