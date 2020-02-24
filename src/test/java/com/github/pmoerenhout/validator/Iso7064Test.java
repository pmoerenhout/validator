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

  public void testIsValidEid() throws Exception {
    Assert.assertTrue(iso7064.isValid("89001012012341234012345678901224"));
    Assert.assertTrue(iso7064.isValid("89001567010203040506070809101152"));
    Assert.assertTrue(iso7064.isValid("89044011112233441122334411223321"));
  }

  public void testIsValidIban() throws Exception {
    // https://www.swift.com/sites/default/files/resources/iban_registry.pdf
    Assert.assertTrue(iso7064.isValid(reformatIban("AL47212110090000000235698741")));
    Assert.assertTrue(iso7064.isValid(reformatIban("AD1200012030200359100100")));
    Assert.assertTrue(iso7064.isValid(reformatIban("AT611904300234573201")));
    Assert.assertTrue(iso7064.isValid(reformatIban("AZ21NABZ00000000137010001944")));
    Assert.assertTrue(iso7064.isValid(reformatIban("BH67BMAG00001299123456")));
    Assert.assertTrue(iso7064.isValid(reformatIban("BE68539007547034")));
    Assert.assertTrue(iso7064.isValid(reformatIban("BA391290079401028494")));
    Assert.assertTrue(iso7064.isValid(reformatIban("BR9700360305000010009795493P1")));
    Assert.assertTrue(iso7064.isValid(reformatIban("BR1800000000141455123924100C2")));
    Assert.assertTrue(iso7064.isValid(reformatIban("BG80BNBG96611020345678")));
    Assert.assertTrue(iso7064.isValid(reformatIban("CR0515202001026284066")));
    Assert.assertTrue(iso7064.isValid(reformatIban("HR1210010051863000160")));
    Assert.assertTrue(iso7064.isValid(reformatIban("CY17002001280000001200527600")));
  }

  private String reformatIban(final String iban){
    return iban.substring(4) + iban.substring(0, 4);
  }

  public void testCalculateEid() throws Exception {
    Assert.assertEquals("24", iso7064.calculate("890010120123412340123456789012"));
    Assert.assertEquals("52", iso7064.calculate("890015670102030405060708091011"));
    Assert.assertEquals("21", iso7064.calculate("890440111122334411223344112233"));
  }

  public void testCalculateIban() throws Exception {
    String iban = "AL00212110090000000235698741";
    String reformattedCode = iban.substring(4) + iban.substring(0, 2);
    Assert.assertEquals("47", iso7064.calculate(reformattedCode));
  }

}