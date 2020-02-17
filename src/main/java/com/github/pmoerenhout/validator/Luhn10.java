package com.github.pmoerenhout.validator;

public class Luhn10 {

  public Luhn10() {
  }

  public boolean isValid(String code) {
    return doLuhn(code, false) % 10 == 0;
  }

  public String calculate(String code) {
    final int digit = 9 - ((doLuhn(code, true) - 1) % 10);
    return Integer.toString(digit);
  }

  private int doLuhn(String s, boolean evenPosition) {
    int sum = 0;
    final int length = s.length();
    for (int i = length - 1; i >= 0; i--) {
      int n = Integer.parseInt(s.substring(i, i + 1));
      if (evenPosition) {
        n *= 2;
        if (n > 9) {
          n = (n % 10) + 1;
        }
      }
      sum += n;
      evenPosition = !evenPosition;
    }
    return sum;
  }
}