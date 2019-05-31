package org.demo.webserver.http;

import org.demo.webserver.server.MyServer;
import org.eclipse.jetty.server.Server;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.net.http.HttpClient.*;
import static java.util.stream.Collectors.toList;

/*
 alt univers 65535
  sudo ip link list

  tcpdump -A -s 0 'tcp port 8888 and (((ip[2:2] - ((ip[0]&0xf)<<2)) - ((tcp[12]&0xf0)>>2)) != 0)' -i lo
 $ sudo apt-get install inotify-tools libnotify-bin
    watch 'inotifywait -e access -e attrib /opt/work/bigfile.txt '
    https://www.opersys.com/LTT/documentation.html
 */
public class App030HttpClientGetStreams {

    private static final int PORT = 8888;

    public static void main(String[] args) throws Exception {

        Server s = MyServer.start(PORT);
        URI uri = URI.create(
                "http://localhost:" + PORT + "/book");
        HttpClient client = getHttpClient();
        CompletableFuture res = searchOnSite(client, uri);
        s.join();
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




    private static HttpClient getHttpClient() {
        return newBuilder()
                .version(Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(5))
                .followRedirects(Redirect.ALWAYS)
                .build();
    }

}

class StringFinder implements Flow.Subscriber<String> {

    AtomicInteger counter = new AtomicInteger(1);
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

        try{
           //if(counter.incrementAndGet()%100_000==0)
                Thread.sleep(1);
        subscription.request(1);
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