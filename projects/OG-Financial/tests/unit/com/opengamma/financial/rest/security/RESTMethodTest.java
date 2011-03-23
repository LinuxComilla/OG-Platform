/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.rest.security;

import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static com.opengamma.financial.rest.security.SecuritySourceServiceNames.DEFAULT_SECURITYSOURCE_NAME;
import static com.opengamma.financial.rest.security.SecuritySourceServiceNames.SECURITYSOURCE_SECURITY;
import java.util.Arrays;
import java.util.Collection;

import javax.time.calendar.LocalDateTime;
import javax.time.calendar.TimeZone;
import javax.time.calendar.ZonedDateTime;

import org.fudgemsg.FudgeField;
import org.fudgemsg.FudgeFieldContainer;
import org.fudgemsg.FudgeMsgEnvelope;
import org.fudgemsg.FudgeMsgFormatter;
import com.opengamma.engine.test.MockSecurity;
import com.opengamma.financial.convention.daycount.DayCountFactory;
import com.opengamma.financial.convention.frequency.SimpleFrequency;
import com.opengamma.financial.convention.frequency.SimpleFrequencyFactory;
import com.opengamma.financial.convention.yield.YieldConventionFactory;
import com.opengamma.financial.security.DateTimeWithZone;
import com.opengamma.financial.security.MockFinancialSecuritySource;
import com.opengamma.financial.security.bond.BondSecurity;
import com.opengamma.financial.security.bond.GovernmentBondSecurity;
import com.opengamma.id.IdentificationScheme;
import com.opengamma.id.Identifier;
import com.opengamma.id.IdentifierBundle;
import com.opengamma.id.UniqueIdentifier;
import com.opengamma.util.fudge.OpenGammaFudgeContext;
import com.opengamma.util.money.Currency;
import com.opengamma.util.time.Expiry;

/**
 * Test RESTful security source.
 */
public class RESTMethodTest {

  private final SecuritySourceService _securitySourceService = new SecuritySourceService(OpenGammaFudgeContext.getInstance());
  private UniqueIdentifier _uid1;

  protected SecuritySourceService getSecuritySourceService() {
    return _securitySourceService;
  }

  protected SecuritySourceResource getSecuritySourceResource() {
    return getSecuritySourceService().findResource(DEFAULT_SECURITYSOURCE_NAME);
  }

  @BeforeMethod
  public void configureService() {
    MockFinancialSecuritySource securitySource = new MockFinancialSecuritySource();
    Identifier secId1 = Identifier.of(IdentificationScheme.of("d1"), "v1");
    Identifier secId2 = Identifier.of(IdentificationScheme.of("d2"), "v2");
    MockSecurity sec1 = new MockSecurity("t1");
    sec1.setIdentifiers(IdentifierBundle.of(secId1));
    sec1.setSecurityType("BOND");
    securitySource.addSecurity(sec1);
    MockSecurity sec2 = new MockSecurity("t2");
    sec2.setIdentifiers(IdentifierBundle.of(secId2));
    securitySource.addSecurity(sec2);
    
    BondSecurity bondSec = new GovernmentBondSecurity("US TREASURY N/B", "Government", "US", "Treasury", Currency.USD,
        YieldConventionFactory.INSTANCE.getYieldConvention("US Treasury equivalent"), new Expiry(ZonedDateTime.of(2011, 2, 1, 12, 0, 0, 0, TimeZone.UTC)), "", 200,
        SimpleFrequencyFactory.INSTANCE.getFrequency(SimpleFrequency.SEMI_ANNUAL_NAME), DayCountFactory.INSTANCE.getDayCount("Actual/Actual"),
        new DateTimeWithZone(LocalDateTime.of(2011, 2, 1, 12, 0)), new DateTimeWithZone(LocalDateTime.of(2011, 2, 1, 12, 0)),
        new DateTimeWithZone(LocalDateTime.of(2011, 2, 1, 12, 0)), 100, 100000000, 5000, 1000, 100, 100);
    bondSec.setIdentifiers(IdentifierBundle.of(Identifier.of("A", "B")));
    securitySource.addSecurity(bondSec);
    
    getSecuritySourceService().setUnderlying(securitySource);
    _uid1 = sec1.getUniqueId();
  }

  @Test
  public void testFindSecuritySource() {
    assertNull(getSecuritySourceService().findResource("woot"));
    assertNotNull(getSecuritySourceResource());
  }

  @Test
  public void testGetSecurityByIdentifier() {
    final FudgeMsgEnvelope fme = getSecuritySourceResource().getSecurity(_uid1.toString());
    assertNotNull(fme);
    final FudgeFieldContainer msg = fme.getMessage();
    assertNotNull(msg);
    FudgeMsgFormatter.outputToSystemOut(msg);
    final FudgeFieldContainer security = msg.getFieldValue(FudgeFieldContainer.class, msg.getByName(SECURITYSOURCE_SECURITY));
    assertNotNull(security);
  }

  @Test
  public void testGetSecurityByBundle() {
    final FudgeMsgEnvelope fme = getSecuritySourceResource().getSecurity(Arrays.asList("d1::v1"));
    assertNotNull(fme);
    final FudgeFieldContainer msg = fme.getMessage();
    assertNotNull(msg);
    FudgeMsgFormatter.outputToSystemOut(msg);
    final FudgeFieldContainer security = msg.getFieldValue(FudgeFieldContainer.class, msg.getByName(SECURITYSOURCE_SECURITY));
    assertNotNull(security);
  }

  @Test
  public void testGetSecurities() {
    final FudgeMsgEnvelope fme = getSecuritySourceResource().getSecurities(Arrays.asList("d1::v1", "d2::v2"));
    assertNotNull(fme);
    final FudgeFieldContainer msg = fme.getMessage();
    assertNotNull(msg);
    FudgeMsgFormatter.outputToSystemOut(msg);
    final Collection<FudgeField> securities = msg.getAllByName(SECURITYSOURCE_SECURITY);
    assertNotNull(securities);
    assertEquals(2, securities.size());
  }

  @Test
  public void testGetAllBondsOfIssuerType() {
    final FudgeMsgEnvelope fme = getSecuritySourceResource().getBondsWithIssuerName("US TREASURY N/B");
    assertNotNull(fme);
    final FudgeFieldContainer msg = fme.getMessage();
    assertNotNull(msg);
    FudgeMsgFormatter.outputToSystemOut(msg);
    final Collection<FudgeField> securities = msg.getAllByName(SECURITYSOURCE_SECURITY);
    assertNotNull(securities);
    assertEquals(1, securities.size());
  }

}
