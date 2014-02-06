/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.interestrate.payments.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opengamma.analytics.financial.interestrate.payments.derivative.CouponONCompounded;
import com.opengamma.analytics.financial.provider.description.interestrate.MulticurveProviderInterface;
import com.opengamma.analytics.financial.provider.sensitivity.multicurve.ForwardSensitivity;
import com.opengamma.analytics.financial.provider.sensitivity.multicurve.MulticurveSensitivity;
import com.opengamma.analytics.financial.provider.sensitivity.multicurve.MultipleCurrencyMulticurveSensitivity;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.money.MultipleCurrencyAmount;
import com.opengamma.util.tuple.DoublesPair;

/**
 *
 */
public final class CouponONCompoundedDiscountingMethod {

  /**
   * The method unique instance.
   */
  private static final CouponONCompoundedDiscountingMethod INSTANCE = new CouponONCompoundedDiscountingMethod();

  /**
   * Return the unique instance of the class.
   * @return The instance.
   */
  public static CouponONCompoundedDiscountingMethod getInstance() {
    return INSTANCE;
  }

  /**
   * Private constructor.
   */
  private CouponONCompoundedDiscountingMethod() {
  }

  /**
   * Computes the present value.
   * @param coupon The coupon.
   * @param multicurve The multi-curve provider.
   * @return The present value.
   */
  public MultipleCurrencyAmount presentValue(final CouponONCompounded coupon, final MulticurveProviderInterface multicurve) {
    ArgumentChecker.notNull(coupon, "Coupon");
    ArgumentChecker.notNull(multicurve, "Market");
    double ratio = 1.0;
    for (int i = 0; i < coupon.getFixingPeriodAccrualFactors().length; i++) {
      ratio *= Math.pow(
          1 + getAnnuallyCompoundedForwardRate(coupon, multicurve, coupon.getFixingPeriodStartTimes()[i], coupon.getFixingPeriodEndTimes()[i], coupon.getFixingPeriodAccrualFactors()[i]),
          coupon.getFixingPeriodAccrualFactors()[i]);
    }
    final double df = multicurve.getDiscountFactor(coupon.getCurrency(), coupon.getPaymentTime());
    final double pv = df * coupon.getNotionalAccrued() * ratio;
    return MultipleCurrencyAmount.of(coupon.getCurrency(), pv);
  }

  /**
   * Compute the present value sensitivity to rates of a OIS coupon by discounting.
   * @param coupon The coupon.
   * @param multicurve The multi-curve provider.
   * @return The present value curve sensitivities.
   */
  public MultipleCurrencyMulticurveSensitivity presentValueCurveSensitivity(final CouponONCompounded coupon, final MulticurveProviderInterface multicurve) {
    ArgumentChecker.notNull(coupon, "Coupon");
    ArgumentChecker.notNull(multicurve, "Multi-curves");
    final double df = multicurve.getDiscountFactor(coupon.getCurrency(), coupon.getPaymentTime());
    double ratio = 1.0;
    final double[] forward = new double[coupon.getFixingPeriodAccrualFactors().length];
    for (int i = 0; i < coupon.getFixingPeriodAccrualFactors().length; i++) {
      forward[i] = getAnnuallyCompoundedForwardRate(coupon, multicurve, coupon.getFixingPeriodStartTimes()[i], coupon.getFixingPeriodEndTimes()[i], coupon.getFixingPeriodAccrualFactors()[i]);
      ratio *= Math.pow(1 + forward[i], coupon.getFixingPeriodAccrualFactors()[i]);
    }
    // Backward sweep
    final double pvBar = 1.0;
    final double ratioBar = coupon.getNotionalAccrued() * df * pvBar;
    final double[] forwardBar = new double[coupon.getFixingPeriodAccrualFactors().length];
    final double[] rebase = new double[coupon.getFixingPeriodAccrualFactors().length];
    for (int i = 0; i < coupon.getFixingPeriodAccrualFactors().length; i++) {
      final double delta = coupon.getFixingPeriodAccrualFactors()[i];
      rebase[i] = Math.pow(
          multicurve.getDiscountFactor(coupon.getCurrency(), coupon.getFixingPeriodStartTimes()[i]) / multicurve.getDiscountFactor(coupon.getCurrency(), coupon.getFixingPeriodEndTimes()[i]),
          (1 - delta) / delta);
      forwardBar[i] = rebase[i] * ratioBar * ratio * coupon.getFixingPeriodAccrualFactors()[i] / (1 + forward[i]);
    }
    final double dfBar = coupon.getNotionalAccrued() * ratio * pvBar;
    final Map<String, List<DoublesPair>> mapDsc = new HashMap<>();
    final List<DoublesPair> listDiscounting = new ArrayList<>();
    listDiscounting.add(DoublesPair.of(coupon.getPaymentTime(), -coupon.getPaymentTime() * df * dfBar));
    mapDsc.put(multicurve.getName(coupon.getCurrency()), listDiscounting);
    final Map<String, List<ForwardSensitivity>> mapFwd = new HashMap<>();
    final List<ForwardSensitivity> listForward = new ArrayList<>();
    for (int i = 0; i < coupon.getFixingPeriodAccrualFactors().length; i++) {
      listForward.add(new ForwardSensitivity(coupon.getFixingPeriodStartTimes()[i], coupon.getFixingPeriodEndTimes()[i], coupon.getFixingPeriodAccrualFactors()[i], forwardBar[i]));
    }
    mapFwd.put(multicurve.getName(coupon.getIndex()), listForward);
    final MultipleCurrencyMulticurveSensitivity result = MultipleCurrencyMulticurveSensitivity.of(coupon.getCurrency(), MulticurveSensitivity.of(mapDsc, mapFwd));
    return result;
  }

  /**
   * Compute the Annually Compounded Forward Rate.
   * @param coupon The coupon.
   * @param multicurve The multi-curve provider.
   * @param startTime the start time
   * @param endTime the end time
   * @param accrualFactor the acrual factor
   * @return The Annually Compounded Forward Rate.
   */
  public double getAnnuallyCompoundedForwardRate(final CouponONCompounded coupon, final MulticurveProviderInterface multicurve, final double startTime, final double endTime,
      final double accrualFactor) {
    ArgumentChecker.isTrue(accrualFactor > 0, "the accrual factor should be stricltly positive");
    final double dicountFactorStart = multicurve.getDiscountFactor(coupon.getCurrency(), startTime);
    final double dicountFactorEnd = multicurve.getDiscountFactor(coupon.getCurrency(), endTime);
    return Math.pow(dicountFactorStart / dicountFactorEnd, 1 / accrualFactor) - 1;

  }

}
