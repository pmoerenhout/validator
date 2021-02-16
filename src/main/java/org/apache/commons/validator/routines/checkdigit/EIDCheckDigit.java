/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.validator.routines.checkdigit;

import java.io.Serializable;

/**
 * <b>IBAN</b> (International Bank Account Number) Check Digit calculation/validation.
 * <p>
 * This routine is based on the ISO 7064 Mod 97,10 check digit calculation routine.
 * <p>
 * The two check digit characters in a IBAN number are the third and fourth characters
 * in the code. For <i>check digit</i> calculation/validation the first four characters are moved
 * to the end of the code.
 *  So <code>CCDDnnnnnnn</code> becomes <code>nnnnnnnCCDD</code> (where
 *  <code>CC</code> is the country code and <code>DD</code> is the check digit). For
 *  check digit calculation the check digit value should be set to zero (i.e.
 *  <code>CC00nnnnnnn</code> in this example.
 * <p>
 * Note: the class does not check the format of the IBAN number, only the check digits.
 * <p>
 * For further information see
 *  <a href="https://www.gsma.com/iot/wp-content/uploads/2016/07/SGP.02_v3.1.pdf">Wikipedia -
 *  IBAN number</a>.
 *
 * @version $Revision$
 * @since Validator 1.4
 */
public final class EIDCheckDigit implements CheckDigit, Serializable {

    private static final int CODE_LEN = 30;

    private static final long serialVersionUID = 6845135929410067458L;

    private static final int MAX_ALPHANUMERIC_VALUE = 35; // Character.getNumericValue('Z')

    /** Singleton EID Number Check Digit instance */
    public static final CheckDigit EID_CHECK_DIGIT = new EIDCheckDigit();

    private static final long MAX = 999999999;

    private static final long MODULUS = 97;

    /**
     * Construct Check Digit routine for IBAN Numbers.
     */
    public EIDCheckDigit() {
    }

    /**
     * Validate the check digit of an IBAN code.
     *
     * @param code The code to validate
     * @return <code>true</code> if the check digit is valid, otherwise
     * <code>false</code>
     */
    @Override
    public boolean isValid(String code) {
        if (code == null || code.length() != CODE_LEN + 2) {
            return false;
        }
        String check = code.substring(code.length() - 2); // CHECKSTYLE IGNORE MagicNumber
        if ("00".equals(check) || "01".equals(check) || "99".equals(check)) {
            return false;
        }
        try {
            int modulusResult = calculateModulus(code);
            return (modulusResult == 1);
        } catch (CheckDigitException  ex) {
            return false;
        }
    }

    /**
     * Calculate the <i>Check Digit</i> for an IBAN code.
     * <p>
     * <b>Note:</b> The check digit is the third and fourth
     * characters and is set to the value "<code>00</code>".
     *
     * @param code The code to calculate the Check Digit for
     * @return The calculated Check Digit as 2 numeric decimal characters, e.g. "42"
     * @throws CheckDigitException if an error occurs calculating
     * the check digit for the specified code
     */
    @Override
    public String calculate(String code) throws CheckDigitException {
        if (code == null || code.length() != CODE_LEN) {
            throw new CheckDigitException("Invalid Code length=" +
                    (code == null ? 0 : code.length()));
        }
        code = code + "00"; //.substring(0, code.length()-2); // CHECKSTYLE IGNORE MagicNumber
        int modulusResult = calculateModulus(code);
        int charValue = (98 - modulusResult); // CHECKSTYLE IGNORE MagicNumber
        String checkDigit = Integer.toString(charValue);
        return (charValue > 9 ? checkDigit : "0" + checkDigit); // CHECKSTYLE IGNORE MagicNumber
    }

    /**
     * Calculate the modulus for a code.
     *
     * @param code The code to calculate the modulus for.
     * @return The modulus value
     * @throws CheckDigitException if an error occurs calculating the modulus
     * for the specified code
     */
    private int calculateModulus(String code) throws CheckDigitException {
        long total = 0;
        for (int i = 0; i < code.length(); i++) {
            int charValue = Character.getNumericValue(code.charAt(i));
            if (charValue < 0 || charValue > MAX_ALPHANUMERIC_VALUE) {
                throw new CheckDigitException("Invalid Character[" +
                        i + "] = '" + charValue + "'");
            }
            total = (charValue > 9 ? total * 100 : total * 10) + charValue; // CHECKSTYLE IGNORE MagicNumber
            if (total > MAX) {
                total = total % MODULUS;
            }
        }
        return (int)(total % MODULUS);
    }

}
