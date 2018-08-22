package org.demo.main;

import model.Trade;
import model.Trades;
import org.xml.sax.SAXException;
import parser.XmlParser;
import util.BusinessHelper;

import javax.validation.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        String path = "rules/trades.xml";
        XmlParser.parseAndDoc(path);
        Trades tradesObj = BusinessHelper.getTrades("2014-05-15");

        Validator validator = getValidator(path);
        Set<ConstraintViolation<Trades>> violations = validator.validate(tradesObj);

        printError(violations);
    }






    private static Validator getValidator(String path) throws FileNotFoundException {
        Configuration<?> config = Validation.byDefaultProvider().configure();
        config.addMapping(new FileInputStream(path));
        ValidatorFactory validatorFactory1 = config.buildValidatorFactory();
        return validatorFactory1.getValidator();
    }

    private static void printError(Set<ConstraintViolation<Trades>> violations) {
        violations.stream().map(v -> v.getMessage() +
                "\nValue :" + v.getLeafBean() +
                "\nroot bean :" + v.getLeafBean().getClass() +
                "\npath : " + (v.getPropertyPath().toString().isEmpty()?v.getLeafBean().getClass(): v.getLeafBean().getClass()) +
                "\n"
        ).forEach(System.out::println);
    }



}
