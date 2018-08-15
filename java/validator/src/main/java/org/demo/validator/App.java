package org.demo.validator;

import model.MFISeriesName;
import model.Values;

import javax.validation.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Hello world!
 * https://docs.jboss.org/hibernate/validator/5.0/reference/en-US/html/
 * validator-customconstraints.html#validator-customconstraints-errormessage
 */
public class App {
    public static void main(String[] args) throws FileNotFoundException {


        //XML Based validation
        Configuration<?> config = Validation.byDefaultProvider().configure();
        config.addMapping(new FileInputStream("mfiseriesname.xml"));
        ValidatorFactory validatorFactory1 = config.buildValidatorFactory();
        Validator validator1 = validatorFactory1.getValidator();


        MFISeriesName mfi = new MFISeriesName(10, "Name", "email", "123");
        List<Values> values = new ArrayList<>();
        for (int i = 0; i < 3;i++) {
            values.add(new Values(i));
        }
        mfi.setValues(values);

        Set<ConstraintViolation<MFISeriesName>> validationErrors1 = validator1.validate(mfi);

        if (!validationErrors1.isEmpty()) {
            for (ConstraintViolation<MFISeriesName> error : validationErrors1) {
                System.out.println(error.getMessageTemplate() + "::" + error.getInvalidValue() + "::" + error.getPropertyPath() + "::" + error.getMessage());

            }
        }


    }
}
