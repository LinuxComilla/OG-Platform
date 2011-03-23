/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.fudgemsg;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.Test;
import org.fudgemsg.FudgeMsgField;
import org.fudgemsg.types.StringFieldType;
import com.opengamma.util.time.Tenor;

/**
 * Test Tenor Fudge support.
 */
public class TenorTest extends FinancialTestBase {

  private static final Tenor s_ref = Tenor.EIGHT_MONTHS;

  @Test
  public void testCycle() {
    assertEquals(s_ref, cycleObject(Tenor.class, s_ref));
  }

  @Test
  public void testFromString() {
    assertEquals(s_ref, getFudgeContext().getFieldValue(Tenor.class,
        FudgeMsgField.of(StringFieldType.INSTANCE, s_ref.getPeriod().toString())));
  }

}
