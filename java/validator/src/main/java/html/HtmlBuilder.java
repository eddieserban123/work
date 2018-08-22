package html;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;
import parser.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlBuilder {

    private static Element generateTable(Bean bean){
        Element result = new Element("table").setAttribute("id","customers");
        result.addContent(new Element("caption").setText(bean.getName()));

        Element header = new Element("tr")
                .addContent(new Element("th").setText("Validation Type"))
                .addContent(new Element("th").setText("Field"))
                .addContent(new Element("th").setText("Constraint ID"))
                .addContent(new Element("th").setText("Constraint name"))
                .addContent(new Element("th").setText("Constraint BusinessMsg"));

        result.addContent(header);
        bean.getHtmlRows().stream().forEach(result::addContent);

        return result;
    }

    public static void generateHtml(String fileName, List<Bean> beans){
        try {
            Document doc = new Document();
            Element head = new Element("head")
                    .addContent(new Element("link")
                            .setAttribute("rel","stylesheet")
                            .setAttribute("href","css/tableStyle.css"));

            Element body = new Element("body");
            beans.stream().forEach(bean -> {
                body.addContent(new Element("p"));
                body.addContent(generateTable(bean));
                body.addContent(new Element("p"));
            });

            Element html = new Element("html").addContent(head).addContent(body);
            doc.addContent(html);

            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.output(doc, new FileWriter("styleHtml" + System.getProperty("file.separator") + fileName));

        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}


