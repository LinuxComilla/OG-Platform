/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.security.swap;

import java.util.Map;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.threeten.bp.LocalDate;

import com.opengamma.financial.convention.businessday.BusinessDayConvention;
import com.opengamma.financial.convention.frequency.Frequency;
import com.opengamma.financial.convention.rolldate.RollConvention;
import com.opengamma.financial.security.FinancialSecurityVisitor;
import com.opengamma.financial.security.irs.FloatingInterestRateSwapLeg;
import com.opengamma.id.ExternalIdBundle;
import com.opengamma.master.security.SecurityDescription;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.money.Currency;

/**
 * Bond total return swap security.
 */
@BeanDefinition
@SecurityDescription(type = BondTotalReturnSwapSecurity.SECURITY_TYPE, description = "Bond total return swap")
public class BondTotalReturnSwapSecurity extends TotalReturnSwapSecurity {

  /** Serialization version */
  private static final long serialVersionUID = 1L;

  /**
   * The security type.
   */
  public static final String SECURITY_TYPE = "BOND_TOTAL_RETURN_SWAP";

  /**
   * The notional currency.
   */
  @PropertyDefinition(validate = "notNull")
  private Currency _notionalCurrency;

  /**
   * The notional amount.
   */
  @PropertyDefinition(validate = "notNull")
  private Double _notionalAmount;

  /**
   * For the builder.
   */
  /* package */BondTotalReturnSwapSecurity() {
    super(SECURITY_TYPE);
  }

  /**
   * @param fundingLeg The funding leg, not null
   * @param assetId The asset external id bundle, not null
   * @param effectiveDate The effective date, not null
   * @param maturityDate The maturity date, not null
   * @param numberOfShares The number of shares, not null
   * @param notionalCurrency The currency of the notional, not null
   * @param notionalAmount The amount of the notional, not null
   * @param paymentSettlementDays The number of days to settle for the payments
   * @param paymentBusinessDayConvention The business day convention for the payments, not null
   * @param paymentFrequency The payment frequency, not null
   * @param rollConvention The payment roll convention, not null
   */
  public BondTotalReturnSwapSecurity(final FloatingInterestRateSwapLeg fundingLeg, final ExternalIdBundle assetId,
      final LocalDate effectiveDate, final LocalDate maturityDate, final Double numberOfShares, final Currency notionalCurrency,
      final Double notionalAmount, final int paymentSettlementDays, final BusinessDayConvention paymentBusinessDayConvention,
      final Frequency paymentFrequency, final RollConvention rollConvention) {
    super(SECURITY_TYPE, fundingLeg, assetId, effectiveDate, maturityDate, paymentSettlementDays, paymentBusinessDayConvention,
        paymentFrequency, rollConvention);
    setNotionalCurrency(notionalCurrency);
    setNotionalAmount(notionalAmount);
  }

  @Override
  public <T> T accept(final FinancialSecurityVisitor<T> visitor) {
    ArgumentChecker.notNull(visitor, "visitor");
    return visitor.visitBondTotalReturnSwapSecurity(this);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code BondTotalReturnSwapSecurity}.
   * @return the meta-bean, not null
   */
  public static BondTotalReturnSwapSecurity.Meta meta() {
    return BondTotalReturnSwapSecurity.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(BondTotalReturnSwapSecurity.Meta.INSTANCE);
  }

  @Override
  public BondTotalReturnSwapSecurity.Meta metaBean() {
    return BondTotalReturnSwapSecurity.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the notional currency.
   * @return the value of the property, not null
   */
  public Currency getNotionalCurrency() {
    return _notionalCurrency;
  }

  /**
   * Sets the notional currency.
   * @param notionalCurrency  the new value of the property, not null
   */
  public void setNotionalCurrency(Currency notionalCurrency) {
    JodaBeanUtils.notNull(notionalCurrency, "notionalCurrency");
    this._notionalCurrency = notionalCurrency;
  }

  /**
   * Gets the the {@code notionalCurrency} property.
   * @return the property, not null
   */
  public final Property<Currency> notionalCurrency() {
    return metaBean().notionalCurrency().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the notional amount.
   * @return the value of the property, not null
   */
  public Double getNotionalAmount() {
    return _notionalAmount;
  }

  /**
   * Sets the notional amount.
   * @param notionalAmount  the new value of the property, not null
   */
  public void setNotionalAmount(Double notionalAmount) {
    JodaBeanUtils.notNull(notionalAmount, "notionalAmount");
    this._notionalAmount = notionalAmount;
  }

  /**
   * Gets the the {@code notionalAmount} property.
   * @return the property, not null
   */
  public final Property<Double> notionalAmount() {
    return metaBean().notionalAmount().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public BondTotalReturnSwapSecurity clone() {
    return JodaBeanUtils.cloneAlways(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      BondTotalReturnSwapSecurity other = (BondTotalReturnSwapSecurity) obj;
      return JodaBeanUtils.equal(getNotionalCurrency(), other.getNotionalCurrency()) &&
          JodaBeanUtils.equal(getNotionalAmount(), other.getNotionalAmount()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getNotionalCurrency());
    hash += hash * 31 + JodaBeanUtils.hashCode(getNotionalAmount());
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("BondTotalReturnSwapSecurity{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  @Override
  protected void toString(StringBuilder buf) {
    super.toString(buf);
    buf.append("notionalCurrency").append('=').append(JodaBeanUtils.toString(getNotionalCurrency())).append(',').append(' ');
    buf.append("notionalAmount").append('=').append(JodaBeanUtils.toString(getNotionalAmount())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code BondTotalReturnSwapSecurity}.
   */
  public static class Meta extends TotalReturnSwapSecurity.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code notionalCurrency} property.
     */
    private final MetaProperty<Currency> _notionalCurrency = DirectMetaProperty.ofReadWrite(
        this, "notionalCurrency", BondTotalReturnSwapSecurity.class, Currency.class);
    /**
     * The meta-property for the {@code notionalAmount} property.
     */
    private final MetaProperty<Double> _notionalAmount = DirectMetaProperty.ofReadWrite(
        this, "notionalAmount", BondTotalReturnSwapSecurity.class, Double.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "notionalCurrency",
        "notionalAmount");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1573783695:  // notionalCurrency
          return _notionalCurrency;
        case -902123592:  // notionalAmount
          return _notionalAmount;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends BondTotalReturnSwapSecurity> builder() {
      return new DirectBeanBuilder<BondTotalReturnSwapSecurity>(new BondTotalReturnSwapSecurity());
    }

    @Override
    public Class<? extends BondTotalReturnSwapSecurity> beanType() {
      return BondTotalReturnSwapSecurity.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code notionalCurrency} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Currency> notionalCurrency() {
      return _notionalCurrency;
    }

    /**
     * The meta-property for the {@code notionalAmount} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Double> notionalAmount() {
      return _notionalAmount;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -1573783695:  // notionalCurrency
          return ((BondTotalReturnSwapSecurity) bean).getNotionalCurrency();
        case -902123592:  // notionalAmount
          return ((BondTotalReturnSwapSecurity) bean).getNotionalAmount();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -1573783695:  // notionalCurrency
          ((BondTotalReturnSwapSecurity) bean).setNotionalCurrency((Currency) newValue);
          return;
        case -902123592:  // notionalAmount
          ((BondTotalReturnSwapSecurity) bean).setNotionalAmount((Double) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((BondTotalReturnSwapSecurity) bean)._notionalCurrency, "notionalCurrency");
      JodaBeanUtils.notNull(((BondTotalReturnSwapSecurity) bean)._notionalAmount, "notionalAmount");
      super.validate(bean);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}