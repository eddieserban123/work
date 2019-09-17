package com.report.util;

import java.time.format.DateTimeFormatter;

public class AppDateFormatter {

    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static DateTimeFormatter getDateFormatter() {
        return formatter;
    }
}
