package org.ScubMembership.PasswordSecurity;

import java.util.regex.Pattern;

public class PasswordValidator {
    private static final String STRONG_PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";

    public static boolean isStrongPassword(String password) {
        return password != null && Pattern.matches(STRONG_PASSWORD_REGEX, password);
    }

}
