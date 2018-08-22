package parser;

import org.jdom2.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BeanClass implements Bean {

    private String name;
    private Constraint constraint;

    public BeanClass(String name, Constraint constraint) {
        this.name = name;
        this.constraint = constraint;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Constraint getConstraint() {
        return constraint;
    }

    public void setConstraint(Constraint constraint) {
        this.constraint = constraint;
    }

    @Override
    public String toString() {
        return "BeanClass{" +
                "name='" + name + '\'' +
                ", constraint=" + constraint +
                '}';
    }

    @Override
    public List<Element> getHtmlRows() {

        Element row = new Element("tr")
                .addContent(new Element("td").setText("Class"))
                .addContent(new Element("td").setText(name))
                .addContent(new Element("td").setText(constraint.getDocId()))
                .addContent(new Element("td").setText(constraint.getName()))
                .addContent(new Element("td").setText(constraint.getBusinessMsg()));

        return Collections.singletonList(row);
    }
}
