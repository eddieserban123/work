package org.demo.validator;

import model.EmployeeXMLValidation;
import model.Values;

import javax.validation.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws FileNotFoundException {


        //XML Based validation
        Configuration<?> config = Validation.byDefaultProvider().configure();
        config.addMapping(new FileInputStream("employeeXMLValidation.xml"));
        ValidatorFactory validatorFactory1 = config.buildValidatorFactory();
        Validator validator1 = validatorFactory1.getValidator();


        EmployeeXMLValidation emp1 = new EmployeeXMLValidation(10, "Name", "email", "123");
        List<Values> values = new ArrayList<>();
        for (int i = 0; i < 3;i++) {
            values.add(new Values(i));
        }
        emp1.setValues(values);

        Set<ConstraintViolation<EmployeeXMLValidation>> validationErrors1 = validator1.validate(emp1);

        if (!validationErrors1.isEmpty()) {
            for (ConstraintViolation<EmployeeXMLValidation> error : validationErrors1) {
                System.out.println(error.getMessageTemplate() + "::" + error.getInvalidValue() + "::" + error.getPropertyPath() + "::" + error.getMessage());

            }
        }


    }
}
