package com.pers.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class DateTimeParserUtil {

    public String dateTimeParser(String dateTime) {
        String regex = "(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}:\\d{2})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dateTime);

        if (matcher.find()) {
            String year = matcher.group(1);
            String month = matcher.group(2);
            String day = matcher.group(3);
            String time = matcher.group(4);

            // Форматирование даты
            return String.format("%s %s.%s.%s", time, day, month, year);
        } else return "";
    }
}
