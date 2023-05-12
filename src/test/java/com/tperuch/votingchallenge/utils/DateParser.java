package com.tperuch.votingchallenge.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateParser {

    public static LocalDateTime parseDateFromString(String dateInText){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return LocalDateTime.parse(dateInText, dateTimeFormatter);
    }
}
