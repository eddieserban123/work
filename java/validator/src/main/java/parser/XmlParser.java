package parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import html.HtmlBuilder;
import model.BusinessMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class XmlParser {

    public static void parseAndDoc(String path) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        NodeList docIds = getDocIdNodeList(path);
        Map<String, String> docIdBusinessMessages = getDocIdMessages();

        List<Bean> beans = new ArrayList<>();

        for (int i = 0; i < docIds.getLength(); i++) {
            Node docId = docIds.item(i);
            String docIdName = docId.getTextContent();

            Node constraint = docId.getParentNode();
            String constraintName = docId.getParentNode().getAttributes().getNamedItem("annotation").getTextContent();

            Node fieldOrClass = constraint.getParentNode();

            if (fieldOrClass.getAttributes().getNamedItem("name") != null) {
                String fieldName = fieldOrClass.getAttributes().getNamedItem("name").getTextContent();

                Node bean = fieldOrClass.getParentNode();
                String beanName = bean.getAttributes().getNamedItem("class").getTextContent();

                addBean(beans, beanName, fieldName, constraintName, docIdName, docIdBusinessMessages.get(docIdName));
            }
            else {
                String beanName = fieldOrClass.getParentNode().getAttributes().getNamedItem("class").getTextContent();

                addBean(beans, beanName, constraintName, docIdName, docIdBusinessMessages.get(docIdName));
            }
        }

        HtmlBuilder.generateHtml("test.html", beans);
    }

    private static NodeList getDocIdNodeList(String path) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        docFactory.setIgnoringElementContentWhitespace(true);

        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(new FileInputStream(path));

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        XPathExpression expr = xPath.compile("//*[@name='docId']");

        return (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
    }

    private static Map<String, String> getDocIdMessages() throws IOException {
        Map<String, String> docIdMessages = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        List<BusinessMessage> businessMessages =
                objectMapper.readValue(
                        new File("src/main/resources/docIds.json"),
                        new TypeReference<List<BusinessMessage>>() {
                        });

        businessMessages.stream().forEach(bm -> docIdMessages.put(bm.getDocId(), bm.getBusinessMessage()));

        return docIdMessages;
    }

    public static String getDocIdMsg(String id) throws IOException {
        return getDocIdMessages().get(id);
    }

    private static void addBean(List<Bean> beans, String beanName, String fieldName, String constraintName, String docId, String docIdMessage) {

        Constraint constraint = new Constraint(constraintName, docId, docIdMessage);
        Optional<Bean> existingBean = beans.stream().filter(b -> b.getName().equals(beanName)).findAny();
        if (existingBean.isPresent()) {
            BeanField bean = (BeanField) existingBean.get();
            Optional<Field> existingField = bean.getFields().stream().filter(f -> f.getName().equals(fieldName)).findAny();

            if (existingField.isPresent()) {
                existingField.get().addConstraint(constraint);
            } else {
                Field field = new Field(fieldName);
                field.addConstraint(constraint);
                bean.addField(field);
            }
        } else {
            Field field = new Field(fieldName);
            field.addConstraint(constraint);

            BeanField bean = new BeanField(beanName);
            bean.addField(field);
            beans.add(bean);
        }
    }

    private static void addBean(List<Bean> beans, String beanName, String constraintName, String docId, String docIdMessage) {
        Constraint constraint = new Constraint(constraintName, docId, docIdMessage);
        BeanClass bean = new BeanClass(beanName, constraint);
        beans.add(bean);
    }
}
