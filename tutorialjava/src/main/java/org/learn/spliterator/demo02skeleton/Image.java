package org.learn.spliterator.demo02skeleton;

import org.jsoup.nodes.Element;

/**
 * Created by eduard on 06/09/17.
 */
public class Image {
    private Element element;

    public Image(Element element) {
        this.element = element;
    }

    public String getURL() {
        return element.attr("abs:src");
    }

}
