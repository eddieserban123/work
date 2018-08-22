package parser;

import org.jdom2.Element;

import java.util.List;

public interface Bean {

    String getName();

    List<Element> getHtmlRows();
}
