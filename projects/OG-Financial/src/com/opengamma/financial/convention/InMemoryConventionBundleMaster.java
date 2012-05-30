/** 
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.convention;

import java.util.ArrayList;
import java.util.Collection;

import javax.time.calendar.Period;

import com.opengamma.core.id.ExternalSchemes;
import com.opengamma.financial.convention.businessday.BusinessDayConvention;
import com.opengamma.financial.convention.businessday.BusinessDayConventionFactory;
import com.opengamma.financial.convention.daycount.DayCount;
import com.opengamma.financial.convention.daycount.DayCountFactory;
import com.opengamma.id.ExternalId;
import com.opengamma.id.ExternalIdBundle;
import com.opengamma.id.ExternalScheme;
import com.opengamma.id.UniqueId;

/**
 * An in-memory, statically initialized master for convention bundles and their meta-data.
 */
public class InMemoryConventionBundleMaster implements ConventionBundleMaster {

  /**
   * Scheme to use when specifying rates with simple descriptions e.g. 'LIBOR O/N', 'LIBOR 1w' etc.
   */
  public static final ExternalScheme SIMPLE_NAME_SCHEME = ExternalScheme.of("Reference Rate Simple Name");
  /**
   * Scheme to use when specifying rates using the OpenGamma Synthetic Ticker system
   */
  public static final ExternalScheme OG_SYNTHETIC_TICKER = ExternalSchemes.OG_SYNTHETIC_TICKER;
  /**
   * Scheme of the unique identifiers generated by this repository.
   */
  public static final ExternalScheme IN_MEMORY_UNIQUE_SCHEME = ExternalScheme.of("In-memory Reference Rate unique");
  /**
   * Data store for the conventions.
   */
  private final ExternalIdBundleMapper<ConventionBundle> _mapper = new ExternalIdBundleMapper<ConventionBundle>(IN_MEMORY_UNIQUE_SCHEME.getName());
  private ConventionBundleMasterUtils _utils;

  /**
   * Creates an instance.
   */
  public InMemoryConventionBundleMaster() {
    init();
  }

  protected void init() {
    _utils = new ConventionBundleMasterUtils(this);
    AUConventions.addFixedIncomeInstrumentConventions(this);
    CAConventions.addFixedIncomeInstrumentConventions(this);
    CHConventions.addFixedIncomeInstrumentConventions(this);
    CHConventions.addTreasuryBondConvention(this);
    CHConventions.addCorporateBondConvention(this);
    DKConventions.addFixedIncomeInstrumentConventions(this);
    EUConventions.addFixedIncomeInstrumentConventions(this);
    GBConventions.addFixedIncomeInstrumentConventions(this);
    GBConventions.addTreasuryBondConvention(this);
    GBConventions.addCorporateBondConvention(this);
    GBConventions.addBondFutureConvention(this);
    INConventions.addFixedIncomeInstrumentConventions(this);
    JPConventions.addFixedIncomeInstrumentConventions(this);
    NZConventions.addFixedIncomeInstrumentConventions(this);
    USConventions.addFixedIncomeInstrumentConventions(this);
    USConventions.addCAPMConvention(this);
    USConventions.addTreasuryBondConvention(this);
    USConventions.addCorporateBondConvention(this);
    USConventions.addBondFutureConvention(this);

    addHUFixedIncomeInstruments();
    addITFixedIncomeInstruments();
    addDEFixedIncomeInstruments();
    addFRFixedIncomeInstruments();
    addSEFixedIncomeInstruments();
    addLUTreasuryBondConvention();
    addLUCorporateBondConvention();
    addBHTreasuryBondConvention();
    addBHCorporateBondConvention();
    addSETreasuryBondConvention();
    addSECorporateBondConvention();
    addFRTreasuryBondConvention();
    addFRCorporateBondConvention();
    addPLTreasuryBondConvention();
    addPLCorporateBondConvention();
    addESTreasuryBondConvention();
    addESCorporateBondConvention();
    addNLTreasuryBondConvention();
    addNLCorporateBondConvention();
    addDETreasuryBondConvention();
    addDECorporateBondConvention();
    addITTreasuryBondConvention();
    addITCorporateBondConvention();
    addHUTreasuryBondConvention();
    addHUCorporateBondConvention();
  }

  public UniqueId add(final ExternalIdBundle bundle, final ConventionBundleImpl convention) {
    final UniqueId uid = _mapper.add(bundle, convention);
    convention.setUniqueId(uid);
    return uid;
  }
  
  @Override
  public ConventionBundleDocument getConventionBundle(final UniqueId uniqueId) {
    return new ConventionBundleDocument(_mapper.get(uniqueId));
  }

  @Override
  public ConventionBundleSearchResult searchConventionBundle(final ConventionBundleSearchRequest request) {
    final Collection<ConventionBundle> collection = _mapper.get(request.getIdentifiers());
    return new ConventionBundleSearchResult(wrapReferenceRatesWithDocuments(collection));
  }

  @Override
  public ConventionBundleSearchResult searchHistoricConventionBundle(final ConventionBundleSearchHistoricRequest request) {
    final Collection<ConventionBundle> collection = _mapper.get(request.getIdentifiers());
    return new ConventionBundleSearchResult(wrapReferenceRatesWithDocuments(collection));
  }

  /*package*/Collection<ConventionBundle> getAll() {
    return _mapper.getAll();
  }

  private Collection<ConventionBundleDocument> wrapReferenceRatesWithDocuments(final Collection<ConventionBundle> referenceRates) {
    final Collection<ConventionBundleDocument> results = new ArrayList<ConventionBundleDocument>(referenceRates.size());
    for (final ConventionBundle referenceRate : referenceRates) {
      results.add(new ConventionBundleDocument(referenceRate));
    }
    return results;
  }

  private void addHUFixedIncomeInstruments() {
    final BusinessDayConvention modified = BusinessDayConventionFactory.INSTANCE.getBusinessDayConvention("Modified Following");
    final BusinessDayConvention following = BusinessDayConventionFactory.INSTANCE.getBusinessDayConvention("Following");
    final DayCount act360 = DayCountFactory.INSTANCE.getDayCount("Actual/360");
    final DayCount act365 = DayCountFactory.INSTANCE.getDayCount("Actual/365");
    //Identifiers for external data 
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFCASHP1D")), "HUFCASHP1D", act360, following, Period.ofDays(1), 0, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFCASHP1M")), "HUFCASHP1M", act360, modified, Period.ofMonths(1), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFCASHP2M")), "HUFCASHP2M", act360, modified, Period.ofMonths(2), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFCASHP3M")), "HUFCASHP3M", act360, modified, Period.ofMonths(3), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFCASHP4M")), "HUFCASHP4M", act360, modified, Period.ofMonths(4), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFCASHP5M")), "HUFCASHP5M", act360, modified, Period.ofMonths(5), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFCASHP6M")), "HUFCASHP6M", act360, modified, Period.ofMonths(6), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFCASHP7M")), "HUFCASHP7M", act360, modified, Period.ofMonths(7), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFCASHP8M")), "HUFCASHP8M", act360, modified, Period.ofMonths(8), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFCASHP9M")), "HUFCASHP9M", act360, modified, Period.ofMonths(9), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFCASHP10M")), "HUFCASHP10M", act360, modified, Period.ofMonths(10), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFCASHP11M")), "HUFCASHP11M", act360, modified, Period.ofMonths(11), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFCASHP12M")), "HUFCASHP12M", act360, modified, Period.ofMonths(12), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFSWAPP2Y")), "HUFSWAPP2Y", act365, modified, Period.ofYears(2), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFSWAPP3Y")), "HUFSWAPP3Y", act365, modified, Period.ofYears(3), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFSWAPP4Y")), "HUFSWAPP4Y", act365, modified, Period.ofYears(4), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFSWAPP5Y")), "HUFSWAPP5Y", act365, modified, Period.ofYears(5), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFSWAPP6Y")), "HUFSWAPP6Y", act365, modified, Period.ofYears(6), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFSWAPP7Y")), "HUFSWAPP7Y", act365, modified, Period.ofYears(7), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFSWAPP8Y")), "HUFSWAPP8Y", act365, modified, Period.ofYears(8), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFSWAPP9Y")), "HUFSWAPP9Y", act365, modified, Period.ofYears(9), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HUFSWAPP10Y")), "HUFSWAPP10Y", act365, modified, Period.ofYears(10), 2, false, null);
  }

  private void addITFixedIncomeInstruments() {
    final BusinessDayConvention modified = BusinessDayConventionFactory.INSTANCE.getBusinessDayConvention("Modified Following");
    final BusinessDayConvention following = BusinessDayConventionFactory.INSTANCE.getBusinessDayConvention("Following");
    final DayCount act360 = DayCountFactory.INSTANCE.getDayCount("Actual/360");
    final DayCount thirty360 = DayCountFactory.INSTANCE.getDayCount("30/360");
    //Identifiers for external data 
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLCASHP1D")), "ITLCASHP1D", act360, following, Period.ofDays(1), 0, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLCASHP1M")), "ITLCASHP1M", act360, modified, Period.ofMonths(1), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLCASHP2M")), "ITLCASHP2M", act360, modified, Period.ofMonths(2), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLCASHP3M")), "ITLCASHP3M", act360, modified, Period.ofMonths(3), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLCASHP4M")), "ITLCASHP4M", act360, modified, Period.ofMonths(4), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLCASHP5M")), "ITLCASHP5M", act360, modified, Period.ofMonths(5), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLCASHP6M")), "ITLCASHP6M", act360, modified, Period.ofMonths(6), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLCASHP7M")), "ITLCASHP7M", act360, modified, Period.ofMonths(7), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLCASHP8M")), "ITLCASHP8M", act360, modified, Period.ofMonths(8), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLCASHP9M")), "ITLCASHP9M", act360, modified, Period.ofMonths(9), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLCASHP10M")), "ITLCASHP10M", act360, modified, Period.ofMonths(10), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLCASHP11M")), "ITLCASHP11M", act360, modified, Period.ofMonths(11), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLCASHP12M")), "ITLCASHP12M", act360, modified, Period.ofMonths(12), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLSWAPP2Y")), "ITLSWAPP2Y", thirty360, modified, Period.ofYears(2), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLSWAPP3Y")), "ITLSWAPP3Y", thirty360, modified, Period.ofYears(3), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLSWAPP4Y")), "ITLSWAPP4Y", thirty360, modified, Period.ofYears(4), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLSWAPP5Y")), "ITLSWAPP5Y", thirty360, modified, Period.ofYears(5), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLSWAPP6Y")), "ITLSWAPP6Y", thirty360, modified, Period.ofYears(6), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLSWAPP7Y")), "ITLSWAPP7Y", thirty360, modified, Period.ofYears(7), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLSWAPP8Y")), "ITLSWAPP8Y", thirty360, modified, Period.ofYears(8), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLSWAPP9Y")), "ITLSWAPP9Y", thirty360, modified, Period.ofYears(9), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLSWAPP10Y")), "ITLSWAPP10Y", thirty360, modified, Period.ofYears(10), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLSWAPP12Y")), "ITLSWAPP12Y", thirty360, modified, Period.ofYears(12), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLSWAPP15Y")), "ITLSWAPP15Y", thirty360, modified, Period.ofYears(15), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLSWAPP20Y")), "ITLSWAPP20Y", thirty360, modified, Period.ofYears(20), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLSWAPP25Y")), "ITLSWAPP25Y", thirty360, modified, Period.ofYears(25), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLSWAPP30Y")), "ITLSWAPP30Y", thirty360, modified, Period.ofYears(30), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLSWAPP40Y")), "ITLSWAPP40Y", thirty360, modified, Period.ofYears(40), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLSWAPP50Y")), "ITLSWAPP50Y", thirty360, modified, Period.ofYears(50), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ITLSWAPP60Y")), "ITLSWAPP80Y", thirty360, modified, Period.ofYears(80), 2, false, null);
  }

  private void addDEFixedIncomeInstruments() {
    final BusinessDayConvention modified = BusinessDayConventionFactory.INSTANCE.getBusinessDayConvention("Modified Following");
    final BusinessDayConvention following = BusinessDayConventionFactory.INSTANCE.getBusinessDayConvention("Following");
    final DayCount act360 = DayCountFactory.INSTANCE.getDayCount("Actual/360");
    final DayCount thirty360 = DayCountFactory.INSTANCE.getDayCount("30/360");
    //Identifiers for external data 
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMCASHP1D")), "DEMCASHP1D", act360, following, Period.ofDays(1), 0, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMCASHP1M")), "DEMCASHP1M", act360, modified, Period.ofMonths(1), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMCASHP2M")), "DEMCASHP2M", act360, modified, Period.ofMonths(2), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMCASHP3M")), "DEMCASHP3M", act360, modified, Period.ofMonths(3), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMCASHP4M")), "DEMCASHP4M", act360, modified, Period.ofMonths(4), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMCASHP5M")), "DEMCASHP5M", act360, modified, Period.ofMonths(5), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMCASHP6M")), "DEMCASHP6M", act360, modified, Period.ofMonths(6), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMCASHP7M")), "DEMCASHP7M", act360, modified, Period.ofMonths(7), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMCASHP8M")), "DEMCASHP8M", act360, modified, Period.ofMonths(8), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMCASHP9M")), "DEMCASHP9M", act360, modified, Period.ofMonths(9), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMCASHP10M")), "DEMCASHP10M", act360, modified, Period.ofMonths(10), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMCASHP11M")), "DEMCASHP11M", act360, modified, Period.ofMonths(11), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMCASHP12M")), "DEMCASHP12M", act360, modified, Period.ofMonths(12), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMSWAPP2Y")), "DEMSWAPP2Y", thirty360, modified, Period.ofYears(2), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMSWAPP3Y")), "DEMSWAPP3Y", thirty360, modified, Period.ofYears(3), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMSWAPP4Y")), "DEMSWAPP4Y", thirty360, modified, Period.ofYears(4), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMSWAPP5Y")), "DEMSWAPP5Y", thirty360, modified, Period.ofYears(5), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMSWAPP6Y")), "DEMSWAPP6Y", thirty360, modified, Period.ofYears(6), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMSWAPP7Y")), "DEMSWAPP7Y", thirty360, modified, Period.ofYears(7), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMSWAPP8Y")), "DEMSWAPP8Y", thirty360, modified, Period.ofYears(8), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMSWAPP9Y")), "DEMSWAPP9Y", thirty360, modified, Period.ofYears(9), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMSWAPP10Y")), "DEMSWAPP10Y", thirty360, modified, Period.ofYears(10), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMSWAPP12Y")), "DEMSWAPP12Y", thirty360, modified, Period.ofYears(12), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMSWAPP15Y")), "DEMSWAPP15Y", thirty360, modified, Period.ofYears(15), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMSWAPP20Y")), "DEMSWAPP20Y", thirty360, modified, Period.ofYears(20), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMSWAPP25Y")), "DEMSWAPP25Y", thirty360, modified, Period.ofYears(25), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMSWAPP30Y")), "DEMSWAPP30Y", thirty360, modified, Period.ofYears(30), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMSWAPP40Y")), "DEMSWAPP40Y", thirty360, modified, Period.ofYears(40), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMSWAPP50Y")), "DEMSWAPP50Y", thirty360, modified, Period.ofYears(50), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DEMSWAPP60Y")), "DEMSWAPP80Y", thirty360, modified, Period.ofYears(80), 2, false, null);
  }

  private void addFRFixedIncomeInstruments() {
    final BusinessDayConvention modified = BusinessDayConventionFactory.INSTANCE.getBusinessDayConvention("Modified Following");
    final BusinessDayConvention following = BusinessDayConventionFactory.INSTANCE.getBusinessDayConvention("Following");
    final DayCount act360 = DayCountFactory.INSTANCE.getDayCount("Actual/360");
    final DayCount thirty360 = DayCountFactory.INSTANCE.getDayCount("30/360");
    //Identifiers for external data 
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFCASHP1D")), "FRFCASHP1D", act360, following, Period.ofDays(1), 0, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFCASHP1M")), "FRFCASHP1M", act360, modified, Period.ofMonths(1), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFCASHP2M")), "FRFCASHP2M", act360, modified, Period.ofMonths(2), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFCASHP3M")), "FRFCASHP3M", act360, modified, Period.ofMonths(3), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFCASHP4M")), "FRFCASHP4M", act360, modified, Period.ofMonths(4), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFCASHP5M")), "FRFCASHP5M", act360, modified, Period.ofMonths(5), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFCASHP6M")), "FRFCASHP6M", act360, modified, Period.ofMonths(6), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFCASHP7M")), "FRFCASHP7M", act360, modified, Period.ofMonths(7), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFCASHP8M")), "FRFCASHP8M", act360, modified, Period.ofMonths(8), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFCASHP9M")), "FRFCASHP9M", act360, modified, Period.ofMonths(9), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFCASHP10M")), "FRFCASHP10M", act360, modified, Period.ofMonths(10), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFCASHP11M")), "FRFCASHP11M", act360, modified, Period.ofMonths(11), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFCASHP12M")), "FRFCASHP12M", act360, modified, Period.ofMonths(12), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFSWAPP2Y")), "FRFSWAPP2Y", thirty360, modified, Period.ofYears(2), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFSWAPP3Y")), "FRFSWAPP3Y", thirty360, modified, Period.ofYears(3), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFSWAPP4Y")), "FRFSWAPP4Y", thirty360, modified, Period.ofYears(4), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFSWAPP5Y")), "FRFSWAPP5Y", thirty360, modified, Period.ofYears(5), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFSWAPP6Y")), "FRFSWAPP6Y", thirty360, modified, Period.ofYears(6), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFSWAPP7Y")), "FRFSWAPP7Y", thirty360, modified, Period.ofYears(7), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFSWAPP8Y")), "FRFSWAPP8Y", thirty360, modified, Period.ofYears(8), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFSWAPP9Y")), "FRFSWAPP9Y", thirty360, modified, Period.ofYears(9), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFSWAPP10Y")), "FRFSWAPP10Y", thirty360, modified, Period.ofYears(10), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFSWAPP12Y")), "FRFSWAPP12Y", thirty360, modified, Period.ofYears(12), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFSWAPP15Y")), "FRFSWAPP15Y", thirty360, modified, Period.ofYears(15), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFSWAPP20Y")), "FRFSWAPP20Y", thirty360, modified, Period.ofYears(20), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFSWAPP25Y")), "FRFSWAPP25Y", thirty360, modified, Period.ofYears(25), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFSWAPP30Y")), "FRFSWAPP30Y", thirty360, modified, Period.ofYears(30), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFSWAPP40Y")), "FRFSWAPP40Y", thirty360, modified, Period.ofYears(40), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFSWAPP50Y")), "FRFSWAPP50Y", thirty360, modified, Period.ofYears(50), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FRFSWAPP60Y")), "FRFSWAPP80Y", thirty360, modified, Period.ofYears(80), 2, false, null);
  }

  private void addSEFixedIncomeInstruments() {
    final BusinessDayConvention modified = BusinessDayConventionFactory.INSTANCE.getBusinessDayConvention("Modified Following");
    final BusinessDayConvention following = BusinessDayConventionFactory.INSTANCE.getBusinessDayConvention("Following");
    final DayCount act360 = DayCountFactory.INSTANCE.getDayCount("Actual/360");
    final DayCount act365 = DayCountFactory.INSTANCE.getDayCount("Actual/365");
    //Identifiers for external data 
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKCASHP1D")), "SEKCASHP1D", act360, following, Period.ofDays(1), 0, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKCASHP1M")), "SEKCASHP1M", act360, modified, Period.ofMonths(1), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKCASHP2M")), "SEKCASHP2M", act360, modified, Period.ofMonths(2), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKCASHP3M")), "SEKCASHP3M", act360, modified, Period.ofMonths(3), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKCASHP4M")), "SEKCASHP4M", act360, modified, Period.ofMonths(4), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKCASHP5M")), "SEKCASHP5M", act360, modified, Period.ofMonths(5), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKCASHP6M")), "SEKCASHP6M", act360, modified, Period.ofMonths(6), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKCASHP7M")), "SEKCASHP7M", act360, modified, Period.ofMonths(7), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKCASHP8M")), "SEKCASHP8M", act360, modified, Period.ofMonths(8), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKCASHP9M")), "SEKCASHP9M", act360, modified, Period.ofMonths(9), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKCASHP10M")), "SEKCASHP10M", act365, modified, Period.ofMonths(10), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKCASHP11M")), "SEKCASHP11M", act365, modified, Period.ofMonths(11), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKCASHP12M")), "SEKCASHP12M", act360, modified, Period.ofMonths(12), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKSWAPP2Y")), "SEKSWAPP2Y", act360, modified, Period.ofYears(2), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKSWAPP3Y")), "SEKSWAPP3Y", act360, modified, Period.ofYears(3), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKSWAPP4Y")), "SEKSWAPP4Y", act360, modified, Period.ofYears(4), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKSWAPP5Y")), "SEKSWAPP5Y", act360, modified, Period.ofYears(5), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKSWAPP6Y")), "SEKSWAPP6Y", act360, modified, Period.ofYears(6), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKSWAPP7Y")), "SEKSWAPP7Y", act360, modified, Period.ofYears(7), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKSWAPP8Y")), "SEKSWAPP8Y", act360, modified, Period.ofYears(8), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKSWAPP9Y")), "SEKSWAPP9Y", act360, modified, Period.ofYears(9), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKSWAPP10Y")), "SEKSWAPP10Y", act360, modified, Period.ofYears(10), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKSWAPP12Y")), "SEKSWAPP12Y", act360, modified, Period.ofYears(12), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKSWAPP15Y")), "SEKSWAPP15Y", act360, modified, Period.ofYears(15), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKSWAPP20Y")), "SEKSWAPP20Y", act360, modified, Period.ofYears(20), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKSWAPP25Y")), "SEKSWAPP25Y", act360, modified, Period.ofYears(25), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKSWAPP30Y")), "SEKSWAPP30Y", act360, modified, Period.ofYears(30), 2, false, null);
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SEKSWAPP40Y")), "SEKSWAPP40Y", act360, modified, Period.ofYears(40), 2, false, null);
  }

  //TODO all of the conventions named treasury need to be changed - after we can differentiate T-bills, Treasuries, etc
  private void addLUTreasuryBondConvention() {
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "LU_TREASURY_BOND_CONVENTION")), "LU_TREASURY_BOND_CONVENTION", true, true, 0, 3, true);
  }

  //TODO all of the conventions named treasury need to be changed
  private void addLUCorporateBondConvention() {
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "LU_CORPORATE_BOND_CONVENTION")), "LU_CORPORATE_BOND_CONVENTION", true, true, 0, 3, true);
  }

  //TODO all of the conventions named treasury need to be changed
  private void addHUTreasuryBondConvention() {
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HU_TREASURY_BOND_CONVENTION")), "HU_TREASURY_BOND_CONVENTION", true, true, 0, 2, true);
  }

  //TODO all of the conventions named treasury need to be changed
  private void addHUCorporateBondConvention() {
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "HU_CORPORATE_BOND_CONVENTION")), "HU_CORPORATE_BOND_CONVENTION", true, true, 0, 2, true);
  }

  //TODO all of the conventions named treasury need to be changed
  private void addITTreasuryBondConvention() {
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "IT_TREASURY_BOND_CONVENTION")), "IT_TREASURY_BOND_CONVENTION", true, true, 0, 3, true);
  }

  //TODO all of the conventions named treasury need to be changed
  private void addITCorporateBondConvention() {
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "IT_CORPORATE_BOND_CONVENTION")), "IT_CORPORATE_BOND_CONVENTION", true, true, 0, 3, true);
  }

  //TODO all of the conventions named treasury need to be changed
  private void addDETreasuryBondConvention() {
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DE_TREASURY_BOND_CONVENTION")), "DE_TREASURY_BOND_CONVENTION", true, true, 0, 3, true);
  }

  //TODO all of the conventions named treasury need to be changed
  private void addDECorporateBondConvention() {
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "DE_CORPORATE_BOND_CONVENTION")), "DE_CORPORATE_BOND_CONVENTION", true, true, 0, 3, true);
  }

  //TODO all of the conventions named treasury need to be changed
  private void addESTreasuryBondConvention() {
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ES_TREASURY_BOND_CONVENTION")), "ES_TREASURY_BOND_CONVENTION", true, true, 0, 3, true);
  }

  //TODO all of the conventions named treasury need to be changed
  private void addESCorporateBondConvention() {
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "ES_CORPORATE_BOND_CONVENTION")), "ES_CORPORATE_BOND_CONVENTION", true, true, 0, 3, true);
  }

  //TODO all of the conventions named treasury need to be changed
  private void addNLTreasuryBondConvention() {
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "NL_TREASURY_BOND_CONVENTION")), "NL_TREASURY_BOND_CONVENTION", true, true, 0, 3, true);
  }

  //TODO all of the conventions named treasury need to be changed
  private void addNLCorporateBondConvention() {
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "NL_CORPORATE_BOND_CONVENTION")), "NL_CORPORATE_BOND_CONVENTION", true, true, 0, 3, true);
  }

  //TODO all of the conventions named treasury need to be changed
  private void addPLTreasuryBondConvention() {
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "PL_TREASURY_BOND_CONVENTION")), "PL_TREASURY_BOND_CONVENTION", true, true, 10, 2, true);
  }

  //TODO all of the conventions named treasury need to be changed
  private void addPLCorporateBondConvention() {
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "PL_CORPORATE_BOND_CONVENTION")), "PL_CORPORATE_BOND_CONVENTION", true, true, 10, 2, true);
  }

  //TODO all of the conventions named treasury need to be changed
  private void addFRTreasuryBondConvention() {
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FR_TREASURY_BOND_CONVENTION")), "FR_TREASURY_BOND_CONVENTION", true, true, 0, 3, true);
  }

  //TODO all of the conventions named treasury need to be changed
  private void addFRCorporateBondConvention() {
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "FR_CORPORATE_BOND_CONVENTION")), "FR_CORPORATE_BOND_CONVENTION", true, true, 0, 3, true);
  }

  //TODO all of the conventions named treasury need to be changed
  private void addBHTreasuryBondConvention() {
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "BH_TREASURY_BOND_CONVENTION")), "BH_TREASURY_BOND_CONVENTION", true, true, 0, 1, true);
  }

  //TODO all of the conventions named treasury need to be changed
  private void addBHCorporateBondConvention() {
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "BH_CORPORATE_BOND_CONVENTION")), "BH_CORPORATE_BOND_CONVENTION", true, true, 0, 1, true);
  }

  //TODO all of the conventions named treasury need to be changed
  private void addSETreasuryBondConvention() {
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SE_TREASURY_BOND_CONVENTION")), "SE_TREASURY_BOND_CONVENTION", true, true, 4, 3, true);
  }

  //TODO all of the conventions named treasury need to be changed
  private void addSECorporateBondConvention() {
    _utils.addConventionBundle(ExternalIdBundle.of(ExternalId.of(SIMPLE_NAME_SCHEME, "SE_CORPORATE_BOND_CONVENTION")), "SE_CORPORATE_BOND_CONVENTION", true, true, 4, 3, true);
  }

}
