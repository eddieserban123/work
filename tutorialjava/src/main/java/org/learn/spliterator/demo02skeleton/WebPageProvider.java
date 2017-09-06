package org.learn.spliterator.demo02skeleton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by eduard on 06/09/17.
 */
public class WebPageProvider {

    private Queue<String> urls = new LinkedList<>();

    public WebPageProvider(String url) {
        urls.add(url);
    }
    public WebPageProvider() {
        urls.add("http://www.evz.ro");
    }


    public Document getPage() {
        org.jsoup.nodes.Document doc = null;

        while (doc == null) {
            String nextPageURL = urls.remove();
            System.out.println("Next page: " + nextPageURL);
            try {
                doc = Jsoup.connect(nextPageURL).get();
            } catch (IOException e) {
                // we'll try the next one on our list
            }
        }

        // get links and put on our queue
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            String newURL = link.attr("abs:href");
            // System.out.println(newURL);
            urls.add(newURL);
        }
        return new Document(doc);
    }



}
