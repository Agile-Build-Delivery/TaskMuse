package com.taskmuse.app.utils;
import java.util.regex.Pattern;

public class ValidationUtils {
    public static boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
        return Pattern.matches(emailPattern, email);
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    public static boolean isValidFullName(String fullName) {
       // can check if the full name contains at least two words (first and last name)
        String[] nameParts = fullName.split(" ");
        return fullName.trim().length() > 0 && nameParts.length >= 2;
    }
}
