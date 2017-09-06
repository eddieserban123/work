package org.learn.spliterator.demo02skeleton;

import org.jsoup.nodes.Document;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by eduard on 06/09/17.
 */
public class WebPageSpliterator implements Spliterator<Document> {

    WebPageProvider provider;

    public WebPageSpliterator(WebPageProvider provider) {
            this.provider = provider;
    }

    public boolean tryAdvance(Consumer<? super Document> action) {
        action.accept(provider.getPage());
        return false;
    }

    public Spliterator<Document> trySplit() {
        return null;
    }

    public long estimateSize() {
        return 0;
    }

    public int characteristics() {
        return 0;
    }
}
