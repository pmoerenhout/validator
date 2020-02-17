package com.github.pmoerenhout.validator;

import org.junit.Assert;

import junit.framework.TestCase;

public class Iso7064Test extends TestCase {

  private Iso7064 iso7064;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    iso7064 = new Iso7064();
  }

  public void testIsValid() throws Exception {
    Assert.assertTrue(iso7064.isValid("89001012012341234012345678901224"));
    Assert.assertTrue(iso7064.isValid("89001567010203040506070809101152"));
    Assert.assertTrue(iso7064.isValid("89044011112233441122334411223321"));
    Assert.assertTrue(iso7064.isValid("89001039240040102300000000149266"));
    Assert.assertFalse(iso7064.isValid("89001039240040102300000000149265"));
    Assert.assertTrue(iso7064.isValid("89001039240040102300000000151303"));
  }

  public void testIsValidIban() throws Exception {
    String iban = "NL02ABNA0123456789";
    String reformattedCode = iban.substring(4) + iban.substring(0, 4);
    Assert.assertTrue(iso7064.isValid(reformattedCode));
  }

  public void testCalculate() throws Exception {
    Assert.assertEquals("24", iso7064.calculate("890010120123412340123456789012"));
    Assert.assertEquals("52", iso7064.calculate("890015670102030405060708091011"));
    Assert.assertEquals("21", iso7064.calculate("890440111122334411223344112233"));
    Assert.assertEquals("66", iso7064.calculate("890010392400401023000000001492"));
    Assert.assertEquals("03", iso7064.calculate("890010392400401023000000001513"));
  }

  public void testCaluculateIban() throws Exception {
    String iban = "NL00ABNA0123456789";
    String reformattedCode = iban.substring(4) + iban.substring(0, 2);
    Assert.assertEquals("02", iso7064.calculate(reformattedCode));
  }

}