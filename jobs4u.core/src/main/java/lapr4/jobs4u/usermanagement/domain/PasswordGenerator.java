/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package lapr4.jobs4u.usermanagement.domain;

import eapli.framework.infrastructure.authz.application.PasswordPolicy;
import eapli.framework.strings.util.StringPredicates;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Enforces that passwords must be at least 8 characters long, uppercase and lowercase letters,
 * digits and at least one non-alphanumeric character.
 * <p>
 * look into
 * https://documentation.cpanel.net/display/CKB/How+to+Determine+Password+Strength
 * for example rules of password strength
 *
 * @author Paulo Gandra de Sousa 24/05/2019
 */
public class PasswordGenerator implements PasswordPolicy {

    /*
     * (non-Javadoc)
     *
     * @see eapli.framework.infrastructure.authz.domain.model.PasswordPolicy#
     * meetsRequeriments(java.lang.String)
     */
    @Override
    public boolean isSatisfiedBy(final String rawPassword) {
        // sanity check
        if (StringPredicates.isNullOrEmpty(rawPassword)) {
            return false;
        }

        // at least 8 characters long
        if (rawPassword.length() < 8) {
            return false;
        }

        // at least one digit
        if (!StringPredicates.containsDigit(rawPassword)) {
            return false;
        }

        // at least one uppercase letter
        if (!StringPredicates.containsCapital(rawPassword)){
            return false;
        }

        // at least one lowercase letter
        if (!containsLowerCase(rawPassword)){
            return false;
        }

        // at least one non-alphanumeric character.
        return containsNonAlphanumeric(rawPassword);
    }

    /**
     * Contains lower case boolean.
     *
     * @param text the text
     * @return the boolean
     */
    public static boolean containsLowerCase(final String text) {
        return text.matches(".*[a-z].*");
    }


    /**
     * Contains non alphanumeric boolean.
     *
     * @param rawPassword the raw password
     * @return the boolean
     */
    public static boolean containsNonAlphanumeric(final String rawPassword) {
        return !(StringPredicates.isNullOrEmpty(rawPassword) && rawPassword.matches(".*[^a-zA-Z0-9].*"));
    }

    private static int counter =1 ;

    /**
     * Generate password string.
     *
     * @return the string
     */
    public static String generatePassword() {
        String password = "#Password" + counter;
        counter++;
        return password.toString();

    }


    /**
     * Check how strong a password is. just for demo purposes.
     *
     * @param rawPassword the string to check
     * @return how strong a password is
     */
    public PasswordStrength strength(final String rawPassword) {
        PasswordStrength passwordStrength = PasswordStrength.WEAK;
        if (rawPassword.length() >= 14 || (rawPassword.length() >= 10
                && StringPredicates.containsAny(rawPassword, "$#!%&?"))) {
            passwordStrength = PasswordStrength.EXCELENT;
        } else if (rawPassword.length() >= 10) {
            passwordStrength = PasswordStrength.GOOD;
        } else if (rawPassword.length() >= 8) {
            passwordStrength = PasswordStrength.WEAK;
        }
        return passwordStrength;
    }

    /**
     * The enum Password strength.
     */
    public enum PasswordStrength {
        /**
         * Weak password strength.
         */
        WEAK,
        /**
         * Good password strength.
         */
        GOOD,
        /**
         * Excelent password strength.
         */
        EXCELENT,
    }
}
