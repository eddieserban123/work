package constraint;

import model.Values;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Size;
import java.util.List;

public class ValuesInRangeValidator implements ConstraintValidator<ValuesInRange, List<Values>> {

    private Size valuesInRange;
    private Integer min;
    private Integer max;

    @Override
    public void initialize(ValuesInRange constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(List<Values> values, ConstraintValidatorContext constraintValidatorContext) {
        if (values == null) {
            return true;
        }

        int sum = values.stream().mapToInt(Values::getVal).sum();
        boolean isValid =  sum > min && sum < max;
        if ( !isValid ) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "{contraint.ValuesInRange.message}"
            ). addConstraintViolation();
        }
        return isValid;

    }
}
