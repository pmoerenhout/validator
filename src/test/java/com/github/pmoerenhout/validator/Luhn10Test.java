package com.github.pmoerenhout.validator;

import junit.framework.TestCase;

public class Luhn10Test extends TestCase {

  private Luhn10 luhn;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    luhn = new Luhn10();
  }

  public void testIsValid() throws Exception {
    assertFalse(luhn.isValid("8910390000003919551"));
    assertTrue(luhn.isValid("8910390000003919552"));
    assertFalse(luhn.isValid("8910390000003919553"));

    assertTrue(luhn.isValid("8910390000003919560"));
    assertTrue(luhn.isValid("8910390000004474805"));

    assertTrue(luhn.isValid("8910390000006955777"));
    assertTrue(luhn.isValid("8910390000006955785"));
    assertTrue(luhn.isValid("8910390000006955793"));
    assertTrue(luhn.isValid("8910390000008801052"));
    assertTrue(luhn.isValid("89314226511114271441"));
  }

  public void testCalculateLuhnDigits() throws Exception {
    assertEquals("6", luhn.calculate("891039000000123456"));
    assertEquals("4", luhn.calculate("891039000000123457"));
    assertEquals("2", luhn.calculate("891039000000123458"));
    assertEquals("0", luhn.calculate("891039000000123459"));
    assertEquals("8", luhn.calculate("891039000000123460"));
    assertEquals("1", luhn.calculate("435673456347657344"));
    assertEquals("5", luhn.calculate("891039000000447480"));
    assertEquals("7", luhn.calculate("891039000000695577"));
    assertEquals("5", luhn.calculate("891039000000695578"));
    assertEquals("3", luhn.calculate("891039000000695579"));
    assertEquals("2", luhn.calculate("891039000000880105"));
  }

  public void testIsValidImei() throws Exception {
    // IMEI
    assertTrue(luhn.isValid("352681050268532"));
    // IMEISV
    assertFalse(luhn.isValid("3526810502685303"));
  }
}