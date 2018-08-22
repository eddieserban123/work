package parser;

import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

public class BeanField implements Bean {

    private String name;
    private List<Field> fields;

    public BeanField(String name) {
        this.name = name;
        fields = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public void addField(Field field) {
        fields.add(field);
    }

    @Override
    public String toString() {
        return "BeanField{" +
                "name='" + name + "\', \n" +
                "fields=" + fields +
                '}';
    }

    @Override
    public List<Element> getHtmlRows() {
        List<Element> rows = new ArrayList<>();

        for (Field field : fields) {
            String nameField = field.getName();

            for (Constraint constraint : field.getConstraints()) {
                Element row = new Element("tr")
                        .addContent(new Element("td").setText("Field"))
                        .addContent(new Element("td").setText(nameField))
                        .addContent(new Element("td").setText(constraint.getDocId()))
                        .addContent(new Element("td").setText(constraint.getName()))
                        .addContent(new Element("td").setText(constraint.getBusinessMsg()));

                rows.add(row);
            }
        }

        return rows;
    }
}
