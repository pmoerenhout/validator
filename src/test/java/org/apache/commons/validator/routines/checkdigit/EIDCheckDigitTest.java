package org.apache.commons.validator.routines.checkdigit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

/**
 * EID (eUICC ID) Check Digits Test.
 *
 * @version $Revision$
 * @since Validator 1.4
 */
public class EIDCheckDigitTest extends AbstractCheckDigitTest {

  /**
   * Constructor
   * @param name test name
   */
  public EIDCheckDigitTest(String name) {
    super(name);
    checkDigitLth = 2;
  }

  /**
   * Set up routine & valid codes.
   */
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    routine = EIDCheckDigit.EID_CHECK_DIGIT;
    valid  = new String[]  {
        "89001039240180124400000000000157",
        "89001039240180124400000000000254",
        "89001039240180124400000000000351",
        "89001039240180124400000000000448",
        // "89001039240240124400000000001224",
        "89001012012341234012345678901224",
        "89001039240170123000000000000237",
        "89001039240060125400000003999238",
        "89001039240060125400000003999335",
        "89001039240060125400000003999432",
        "89001039240060125400000003999529",
        "89001039240060125400000003999626",
        "89001039240060112200000000486783",
        "89001039240060114800000000453298",
        "89001039240060114600000001281202",
        "89001567010203040506070809101152",
        "89044011112233441122334411223321"
    };
    /*
     *  sources
     *
     */
    invalid = new String[] {
        "510007+47061BE63",
        "8901101201234123401234567890126545342501",
        "890010",
    };
    zeroSum = null;
    missingMessage = "Invalid Code length=0";
  }

  /**
   * Test zero sum
   */
  @Override
  public void testZeroSum() {
    // ignore, don't run this test

    // example code used to create dummy IBANs
//        try {
//            for(int i=0; i<97;i++) {
//                String check = String.format("ZZ00ZZZZZZZZZZZZZZZZZZZZZZZZZ%02d", new Object[]{Integer.valueOf(i)});
//                String chk = routine.calculate(check);
//                if (chk.equals("97")||chk.equals("98")||chk.equals("02")) {
//                    System.out.println(check+ " "+chk);
//                }
//            }
//        } catch (CheckDigitException e) {
//            e.printStackTrace();
//        }
  }

  /**
   * Returns an array of codes with invalid check digits.
   *
   * @param codes Codes with valid check digits
   * @return Codes with invalid check digits
   */
  @Override
  protected String[] createInvalidCodes(String[] codes) {
    List<String> list = new ArrayList<String>();

    // create invalid check digit values
    for (int i = 0; i < codes.length; i++) {
      String code = removeCheckDigit(codes[i]);
      String check  = checkDigit(codes[i]);
      for (int j = 2; j <= 98; j++) { // check digits can be from 02-98 (00 and 01 are not possible)
        String curr =  j > 9 ? "" + j : "0" + j;
        if (!curr.equals(check)) {
          list.add(code + curr);
        }
      }
    }

    return list.toArray(new String[list.size()]);
  }

  /**
   * Returns a code with the Check Digits (i.e. characters 3&4) set to "00".
   *
   * @param code The code
   * @return The code with the zeroed check digits
   */
  @Override
  protected String removeCheckDigit(String code) {
    return code.substring(0, code.length() - checkDigitLth);
  }

  /**
   * Returns the check digit (i.e. last character) for a code.
   *
   * @param code The code
   * @return The check digit
   */
  @Override
  protected String checkDigit(String code) {
    if (code == null || code.length() <= checkDigitLth) {
      return "";
    }
    return code.substring(code.length() - checkDigitLth);
  }

  public void testOther() throws Exception {
//    BufferedReader rdr = null;
//    try {
//      rdr = new BufferedReader(
//          new InputStreamReader(
//              this.getClass().getResourceAsStream("IBANtests.txt"),"ASCII"));
//      String line;
//      while((line=rdr.readLine()) != null) {
//        if (!line.startsWith("#") && line.length() > 0) {
//          if (line.startsWith("-")) {
//            line = line.substring(1);
//            Assert.assertFalse(line, routine.isValid(line.replaceAll(" ", "")));
//          } else {
//            Assert.assertTrue(line, routine.isValid(line.replaceAll(" ", "")));
//          }
//        }
//      }
//    } finally {
//      if (rdr != null) {
//        rdr.close();
//      }
//    }
  }

  public void testCalculate() throws Exception {
    Assert.assertEquals("24", routine.calculate("890010120123412340123456789012"));
    Assert.assertEquals("52", routine.calculate("890015670102030405060708091011"));
    Assert.assertEquals("21", routine.calculate("890440111122334411223344112233"));
    Assert.assertEquals("66", routine.calculate("890010392400401023000000001492"));
    Assert.assertEquals("03", routine.calculate("890010392400401023000000001513"));
    //Assert.assertEquals("24", routine.calculate("890010392402401244000000000012"));
  }
}
