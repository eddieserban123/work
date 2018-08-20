package xmlparser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.FileInputStream;
import java.io.IOException;

public class xmlParserApp {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        docFactory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(new FileInputStream("mfiseriesname.xml"));
        // Get the root element
        // Node rootEl = doc.getFirstChild();

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile("//*[@name='docId']");
        NodeList beans = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < beans.getLength(); i++) {
            Node field = beans.item(i);
            String name = field.getTextContent();
            String parent = field.getParentNode().getAttributes().getNamedItem("annotation").getTextContent();

            NodeList constraints = field.getChildNodes();
            for (int j = 0; j < constraints.getLength(); j++) {
                Node constraint = constraints.item(j);
                String annotation = constraint.
                        getAttributes().
                        getNamedItem("annotation").
                        getTextContent();
                System.out.println(parent + " " + name + " " + annotation);
            }


        }

    }
}
