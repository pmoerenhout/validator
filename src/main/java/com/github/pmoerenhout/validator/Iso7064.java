package com.github.pmoerenhout.validator;

public class Iso7064 {

  private static final int CHARCODE_0 = '0';
  private static final int CHARCODE_A = 'A';
  private static final String FORMAT = "^[0-9A-Z]{1,}$";

  public boolean isValid(String code) {
    return 1 == compute(code);
  }

  public String calculate(String code) {
    final int calculated = 98 - compute(code + "00");
    if (calculated < 10) {
      return "0" + calculated;
    }
    return Integer.toString(calculated);
  }

  /**
   * Check requirements. Letters are replaced into the string by digits, according to ISO 7064. Then the string is interpreted into a number.
   *
   * @param rawValue - must be not `null` - must respect format `^[0-9A-Z]{1,}$`
   * @return Modulo 97 of the interpreted number
   */
  public int compute(String rawValue) throws IllegalArgumentException {
    if (rawValue == null || !rawValue.matches(FORMAT)) {
      throw new IllegalArgumentException(String.format("Invalid data format; expecting '%s', found: '%s'.", FORMAT, rawValue));
    }
    return mod97(rawValue);
  }

  /**
   * Does NOT check requirements. Letters are replaced into the string by digits, according to ISO 7064. Then the string is interpreted into a number.
   *
   * @param rawValue - must be not `null` - must respect format `^[0-9A-Z]{1,}$`
   * @return Modulo 97 of the interpreted number
   */
  public int computeWithoutCheck(String rawValue) {
    return mod97(rawValue);
  }

  private int mod97(String value) {
    int buffer = 0;

    for (int i = 0; i < value.length(); i++) {
      int charCode = value.charAt(i);
      buffer = charCode + (charCode >= CHARCODE_A ? buffer * 100 - CHARCODE_A + 10 : buffer * 10 - CHARCODE_0);
      if (buffer > 10000000) {
        buffer %= 97;
      }
    }
    return buffer % 97;
  }
}
