package com.library.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // GET TODAY DATE
    public static String today() {
        return LocalDate.now().format(FORMATTER);
    }

    // VALIDATE DATE (SAFE)
    public static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // CHECK OVERDUE
    public static boolean isOverdue(String dueDate) {
        try {
            LocalDate today = LocalDate.now();
            LocalDate due = LocalDate.parse(dueDate, FORMATTER);
            return today.isAfter(due);
        } catch (Exception e) {
            return false;
        }
    }

    // COMPARE DATE RANGE
    public static boolean isWithinRange(String date, String start, String end) {
        try {
            LocalDate d = LocalDate.parse(date, FORMATTER);
            LocalDate s = LocalDate.parse(start, FORMATTER);
            LocalDate e = LocalDate.parse(end, FORMATTER);

            return (d.isEqual(s) || d.isAfter(s)) &&
                    (d.isEqual(e) || d.isBefore(e));
        } catch (Exception ex) {
            return false;
        }
    }
}