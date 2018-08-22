package constraint.annotations;

import constraint.validators.DifferentTCAOpenPriceWithinAQRIDTradeDate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {DifferentTCAOpenPriceWithinAQRIDTradeDate.class})
@Documented
public @interface DifferentTCAOpenPriceWithinAQRIDTradeDateRule {

    String message() default "{constraint.message}";

    Class<?>[] groups() default {};

    String docId() default "0";

    Class<? extends Payload>[] payload() default {};
}



