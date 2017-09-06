package org.learn.spliterator.demo02skeleton;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by eduard on 06/09/17.
 */
public class App {

    public static void main(String[] args) {

//        WebPageProvider provider = new WebPageProvider();
//        System.out.println(provider.getPage());
//        System.out.println(provider.getPage());



        Stream<Document> streams =  StreamSupport.stream(new WebPageSpliterator(new WebPageProvider()), false);
        streams.map(doc -> doc.getElementsByTag("img").first().absUrl("src"));


    }
}
