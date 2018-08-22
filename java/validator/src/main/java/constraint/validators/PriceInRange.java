package constraint.validators;

import constraint.annotations.PriceInRangeRule;
import parser.XmlParser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;

public class PriceInRange implements ConstraintValidator<PriceInRangeRule, Integer> {

    private int min;
    private int max;
    private String docId;

    @Override
    public void initialize(PriceInRangeRule constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
        docId = constraintAnnotation.docId();
    }

    @Override
    public boolean isValid(Integer price, ConstraintValidatorContext constraintValidatorContext) {

        boolean isValid = min < price && price < max;

        StringBuilder sb = new StringBuilder();
        try {
            sb.append("error value: ").append(price).append("\nbusiness check: ").append(XmlParser.getDocIdMsg(docId));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    sb.toString()
            ).addConstraintViolation();
        }

        return isValid;
    }
}
