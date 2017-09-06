package org.learn.spliterator.demo02skeleton;



import java.util.List;
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
        streams.map(Document::getImages).flatMap(List::stream).limit(10).map(i-> i.getURL()).forEach(System.out::println);


    }
}
