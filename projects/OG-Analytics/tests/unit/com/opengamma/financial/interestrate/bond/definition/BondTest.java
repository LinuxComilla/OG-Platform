/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.interestrate.bond.definition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;

import org.junit.Test;

/**
 * 
 */
public class BondTest {
  private static final String BOND_CURVE = "A curve";
  private static final double COUPON = 0.2;
  private static final double YEAR_FRACTION = 1.0;
  private static final double[] COUPONS;
  private static final double[] TIMES = new double[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

  private static final double[] YEAR_FRACTIONS;
  private static final double[] PAYMENTS;
  private static final Bond BOND = new Bond(TIMES, COUPON, BOND_CURVE);

  static {
    int n = TIMES.length;
    PAYMENTS = new double[n];
    COUPONS = new double[n];
    YEAR_FRACTIONS = new double[n];
    Arrays.fill(COUPONS, COUPON);
    Arrays.fill(YEAR_FRACTIONS, YEAR_FRACTION);
    for (int i = 0; i < n; i++) {
      PAYMENTS[i] = COUPON * YEAR_FRACTIONS[i] + (i == (n - 1) ? 1.0 : 0.0);
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullTimes1() {
    new Bond(null, COUPON, BOND_CURVE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullTimes2() {
    new Bond(null, COUPON, YEAR_FRACTION, 0.0, BOND_CURVE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullTimes3() {
    new Bond(null, COUPONS, YEAR_FRACTIONS, 0.0, BOND_CURVE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyTimes1() {
    new Bond(new double[0], COUPON, BOND_CURVE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyTimes2() {
    new Bond(new double[0], COUPON, YEAR_FRACTION, 0.0, BOND_CURVE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyTimes3() {
    new Bond(new double[0], COUPONS, YEAR_FRACTIONS, 0.0, BOND_CURVE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullCoupons1() {
    new Bond(TIMES, null, YEAR_FRACTIONS, 0.0, BOND_CURVE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyCoupons1() {
    new Bond(TIMES, new double[0], YEAR_FRACTIONS, 0.0, BOND_CURVE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullYearFraction1() {
    new Bond(TIMES, COUPONS, null, 0.0, BOND_CURVE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyYearFraction1() {
    new Bond(TIMES, COUPONS, new double[0], 0.0, BOND_CURVE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullName1() {
    new Bond(TIMES, COUPON, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullName2() {
    new Bond(TIMES, COUPON, YEAR_FRACTION, 0.0, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullName3() {
    new Bond(TIMES, COUPONS, YEAR_FRACTIONS, 0.0, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWrongCoupons1() {
    new Bond(TIMES, new double[] {1, 2, 3, 4, 5}, YEAR_FRACTIONS, 0.0, BOND_CURVE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWrongYearFraction1() {
    new Bond(TIMES, COUPONS, new double[] {1, 2, 3, 4, 5}, 0.0, BOND_CURVE);
  }

  @Test
  public void testGetters() {
    assertEquals(BOND.getPrinciplePayment().getFundingCurveName(), BOND_CURVE);
    assertEquals(0.0, BOND.getAccruedInterest(), 0.0);
    assertEquals(BOND.getPrinciplePayment().getPaymentTime(), TIMES[TIMES.length - 1], 0.0);
    Bond other = new Bond(TIMES, COUPONS, YEAR_FRACTIONS, 0.1, BOND_CURVE);
    assertEquals(0.1, other.getAccruedInterest(), 0.0);
  }

  @Test
  public void testHashCodeAndEquals() {

    Bond other = new Bond(TIMES, COUPON, BOND_CURVE);
    assertEquals(other, BOND);
    assertEquals(other.hashCode(), BOND.hashCode());
    other = new Bond(new double[] {1, 2, 3, 4, 5, 6, 7, 8.1, 9, 10}, COUPON, BOND_CURVE);
    assertFalse(other.equals(BOND));
    other = new Bond(TIMES, TIMES, YEAR_FRACTIONS, 0.0, BOND_CURVE);
    assertFalse(other.equals(BOND));
    other = new Bond(TIMES, COUPON, "sfdfsdfs");
    assertFalse(other.equals(BOND));
    other = new Bond(TIMES, COUPONS, YEAR_FRACTIONS, 0.1, BOND_CURVE);
    assertFalse(other.equals(BOND));
    other = new Bond(TIMES, COUPONS, YEAR_FRACTIONS, 0.0, BOND_CURVE);
    assertEquals(other, BOND); //
  }
}
