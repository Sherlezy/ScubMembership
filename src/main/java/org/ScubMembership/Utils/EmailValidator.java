package org.ScubMembership.Utils;

import java.util.regex.Pattern;
//Classe statica, deterministica senza dipendenze esterne. Non uso mockito

public class EmailValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9]+([._%+-]?[A-Za-z0-9]+)*@[A-Za-z0-9]+(-?[A-Za-z0-9]+)*(\\.[A-Za-z]{2,6})+$");

    public static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
