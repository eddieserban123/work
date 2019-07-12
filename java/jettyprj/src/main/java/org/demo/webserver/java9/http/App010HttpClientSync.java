package org.demo.webserver.java9.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.net.http.HttpClient.*;

public class App010HttpClientSync {

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = newBuilder()
                .version(Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(5))
                .followRedirects(Redirect.ALWAYS)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://hotnews.ro"))
                .headers("Accept-Language", "*/*")
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());


    }
}
