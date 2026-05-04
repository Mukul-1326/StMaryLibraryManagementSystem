package com.library.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationUtil {

    // CHECK NUMERIC
    public static boolean isNumeric(String value) {
        return value != null && value.matches("\\d+");
    }

    // VALIDATE EMAIL (IMPROVED)
    public static boolean isValidEmail(String email) {
        return email != null &&
                email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    // VALIDATE DATE FORMAT + REAL DATE
    public static boolean isValidDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // CHECK EMPTY
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    // GENERIC VALIDATION MESSAGE
    public static void showError(String message) {
        System.out.println("Error: " + message);
    }
}