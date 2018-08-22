package constraint.validators;

import constraint.annotations.DifferentTCAOpenPriceWithinAQRIDTradeDateRule;
import model.Trade;
import model.Trades;
import parser.XmlParser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class DifferentTCAOpenPriceWithinAQRIDTradeDate implements ConstraintValidator<DifferentTCAOpenPriceWithinAQRIDTradeDateRule, Trades> {

    private String docId;

    @Override
    public void initialize(DifferentTCAOpenPriceWithinAQRIDTradeDateRule constraintAnnotation) {
        docId = constraintAnnotation.docId();
    }

    @Override
    public boolean isValid(Trades trades, ConstraintValidatorContext constraintValidatorContext) {
        List<List<Trade>> invalidLists =
                trades.getTrades().stream()
                        .collect(groupingBy(Trade::getAqrId))
                        .values()
                        .stream()
                        .map(trList -> trList.stream()
                                .distinct()
                                .collect(toList()))
                        .filter(l -> l.size() > 1)
                        .collect(toList());

        boolean isValid = invalidLists.isEmpty();

        StringBuilder sb = new StringBuilder();
        try {
            sb.append("error values: ").append(invalidLists).append("\nbusiness check: ").append(XmlParser.getDocIdMsg(docId));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if ( !isValid ) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
            sb.toString()
            ).addConstraintViolation();
        }
        return isValid;

    }
}
