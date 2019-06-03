package org.demo.webserver.http;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import static java.net.http.HttpClient.*;

/*
 alt univers 65535
  sudo ip link list

  tcpdump -A -s 0 'tcp port 8888 and (((ip[2:2] - ((ip[0]&0xf)<<2)) - ((tcp[12]&0xf0)>>2)) != 0)' -i lo
 */
public class App031HttpClientPostStreams {


    public static void main(String[] args) throws Exception {

        URI uri = URI.create(
                "http://httpbin.org/post");
        HttpClient client = getHttpClient();
        CompletableFuture res = uploadData(client, uri);
        res.join();
    }


    private static CompletableFuture uploadData(HttpClient client, URI uri) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(new LoggingBodyPublisher(BodyPublishers.ofFile(Path.of("/opt/work/bigfile.txt"))))
                    .uri(uri)
                    .headers("Content-Type", "*/*")
                    .build();
            return client.sendAsync(request,
                    HttpResponse.BodyHandlers.discarding());
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


class LoggingBodyPublisher implements BodyPublisher {


    private BodyPublisher bodypublisher;

    public LoggingBodyPublisher(BodyPublisher bodypublisher) {
        this.bodypublisher = bodypublisher;
    }

    @Override
    public long contentLength() {
        long len = bodypublisher.contentLength();
        System.out.println("content-length :" + len);
        return len;
    }

    @Override
    public void subscribe(Subscriber<? super ByteBuffer> subscriber) {
        bodypublisher.subscribe(new LoggingSubScriber(subscriber));
    }
}

class LoggingSubScriber implements Subscriber<ByteBuffer> {

    private Subscriber<? super ByteBuffer> subscriber;

    public LoggingSubScriber(Subscriber<? super ByteBuffer> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscriber.onSubscribe(new LoggingSubscription(subscription));
    }

    @Override
    public void onNext(ByteBuffer item) {
        System.out.println(" OnNext triggered "  + item.array().length);
        subscriber.onNext(item);
    }

    @Override
    public void onError(Throwable throwable) {
        subscriber.onError(throwable);

    }

    @Override
    public void onComplete() {
        subscriber.onComplete();
    }
}

class LoggingSubscription implements Subscription {

    Subscription subscription;

    public LoggingSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void request(long n) {
        System.out.println(" Requested " + n);
        subscription.request(n);
    }

    @Override
    public void cancel() {
        subscription.cancel();
    }
}