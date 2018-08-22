package parser;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private String name;
    private List<Constraint>  constraints;

    public Field(String name) {
        this.name = name;
        constraints = new ArrayList<>();
    }

    public Field(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    public void addConstraint(Constraint constraint) {
        constraints.add(constraint);
    }

    @Override
    public String toString() {
        return "Field{" +
                "name='" + name + '\'' +
                ", constraints=" + constraints +
                "}\n";
    }
}
