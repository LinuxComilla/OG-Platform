/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.credit.creditdefaultswap.definition.standard;

import org.threeten.bp.ZonedDateTime;

import com.opengamma.analytics.financial.credit.BuySellProtection;
import com.opengamma.analytics.financial.credit.DebtSeniority;
import com.opengamma.analytics.financial.credit.RestructuringClause;
import com.opengamma.analytics.financial.credit.StubType;
import com.opengamma.analytics.financial.credit.collateralmodel.CreditSupportAnnexDefinition;
import com.opengamma.analytics.financial.credit.creditdefaultswap.StandardCDSCoupon;
import com.opengamma.analytics.financial.credit.obligor.definition.Obligor;
import com.opengamma.financial.convention.businessday.BusinessDayConvention;
import com.opengamma.financial.convention.calendar.Calendar;
import com.opengamma.financial.convention.daycount.DayCount;
import com.opengamma.financial.convention.frequency.PeriodFrequency;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.money.Currency;

/**
 * 
 */
public class StandardCollateralisedVanillaCreditDefaultSwapDefinition extends StandardVanillaCreditDefaultSwapDefinition {

  //----------------------------------------------------------------------------------------------------------------------------------------

  // TODO : Need to add the test file for this object
  // TODO : Need to add the hashCode and equals methods

  // ----------------------------------------------------------------------------------------------------------------------------------------

  // Member variables specific to the standard collateralised CDS contract

  // The CSA that this trade is executed under (specifies the details of the collateral arrangements between the protection buyer and seller)
  private final CreditSupportAnnexDefinition _creditSupportAnnex;

  // ----------------------------------------------------------------------------------------------------------------------------------------

  // Ctor for the Standard CDS contract

  public StandardCollateralisedVanillaCreditDefaultSwapDefinition(
      final BuySellProtection buySellProtection,
      final Obligor protectionBuyer,
      final Obligor protectionSeller,
      final Obligor referenceEntity,
      final Currency currency,
      final DebtSeniority debtSeniority,
      final RestructuringClause restructuringClause,
      final Calendar calendar,
      final ZonedDateTime startDate,
      final ZonedDateTime effectiveDate,
      final ZonedDateTime maturityDate,
      final StubType stubType,
      final PeriodFrequency couponFrequency,
      final DayCount daycountFractionConvention,
      final BusinessDayConvention businessdayAdjustmentConvention,
      final boolean immAdjustMaturityDate,
      final boolean adjustEffectiveDate,
      final boolean adjustMaturityDate,
      final double notional,
      final double recoveryRate,
      final boolean includeAccruedPremium,
      final boolean protectionStart,
      final double quotedSpread,
      final StandardCDSCoupon premiumLegCoupon,
      final double upfrontAmount,
      final ZonedDateTime cashSettlementDate,
      final boolean adjustCashSettlementDate,
      final CreditSupportAnnexDefinition creditSupportAnnex) {

    // ----------------------------------------------------------------------------------------------------------------------------------------

    // Call the ctor for the StandardVanillaCreditDefaultSwapDefinition superclass (corresponding to the CDS characteristics common to all types of CDS)

    super(buySellProtection,
        protectionBuyer,
        protectionSeller,
        referenceEntity,
        currency,
        debtSeniority,
        restructuringClause,
        calendar,
        startDate,
        effectiveDate,
        maturityDate,
        stubType,
        couponFrequency,
        daycountFractionConvention,
        businessdayAdjustmentConvention,
        immAdjustMaturityDate,
        adjustEffectiveDate,
        adjustMaturityDate,
        notional,
        recoveryRate,
        includeAccruedPremium,
        protectionStart,
        quotedSpread,
        premiumLegCoupon,
        upfrontAmount,
        cashSettlementDate,
        adjustCashSettlementDate);

    // ----------------------------------------------------------------------------------------------------------------------------------------

    ArgumentChecker.notNull(creditSupportAnnex, "Credit support annex");

    _creditSupportAnnex = creditSupportAnnex;

    // ----------------------------------------------------------------------------------------------------------------------------------------
  }

  public CreditSupportAnnexDefinition getCreditSupportAnnex() {
    return _creditSupportAnnex;
  }
}
