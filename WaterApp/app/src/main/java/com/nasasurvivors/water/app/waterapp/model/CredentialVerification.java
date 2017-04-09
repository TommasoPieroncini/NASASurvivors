package com.nasasurvivors.water.app.waterapp.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zach Schlesinger on 3/4/17.
 */

public class CredentialVerification {

    private static final Pattern p = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x07\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x07\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

    /**
     * email verification method
     * @param email email to verify
     * @return true if valid
     */
    public static boolean verifyEmail(String email) {
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * password verification method
     * @param password password to verify
     * @return true if valid
     */
    public static String verifyPassword(String password) {
        String message = "";
        boolean length = password.length() >= 8;
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasNumber = password.matches(".*\\d+.*");

        message += (!length) ? "Password is too short" : "";
        message += (!hasUppercase) ? "\nPassword does not contain an uppercase" : "";
        message += (!hasLowercase) ? "\nPassword does not contain a lowercase" : "";
        message += (!hasNumber) ? "\nPassword does not contain a number" : "";

        return message;
    }



}