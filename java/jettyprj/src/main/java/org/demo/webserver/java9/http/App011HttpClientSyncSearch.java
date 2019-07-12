package org.demo.webserver.java9.http;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.net.http.HttpClient.*;
import static java.util.stream.Collectors.toList;

public class App011HttpClientSyncSearch {

    private static final String TERM = "genius";


    public static void main(String[] args) throws IOException, InterruptedException {


        List<URI> uris = List.of(
                "https://en.wikipedia.org/wiki/Albert_Einstein",
                "https://en.wikipedia.org/wiki/Adrian_Minune")
                .stream().map(URI::create).collect(toList());

        HttpClient client = getHttpClient();

        searchOnSites(client, uris);


    }

    private static HttpClient getHttpClient() {
        return newBuilder()
                .version(Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(5))
                .followRedirects(Redirect.ALWAYS)
                .build();
    }

    private static void searchOnSites(final HttpClient client, List<URI> uris) {
        uris.stream().map(uri -> {
            try {
                HttpResponse<String> response = null;
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(uri)
                        .headers("Accept-Language", "*/*")
                        .build();

                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                return new SimpleImmutableEntry(uri, countMatches(response.body(), TERM));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).filter(e -> e != null).forEach(e -> System.out.println("uri : " + e.getKey() + " --> " + e.getValue()));

    }


    private static int countMatches(String text, String textToFind) {
        Matcher m = Pattern.compile(textToFind).matcher(text);
        int matches = 0;
        while (m.find())
            matches++;
        return matches;
    }

}
