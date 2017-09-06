package org.learn.spliterator.demo01simpleusage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by eduard on 06/09/17.
 */
public class App {

    public static void main( String[] args ) throws IOException {
        System.out.println( "Hello World!" );
        String url = "http://www.evz.ro";
        Document doc = Jsoup.connect(url).get();

        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");


        for (Element src : media) {
            if (src.tagName().equals("img"))
                print(" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));

            else
                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
        }


    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }

}
