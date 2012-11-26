/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.engine.view.helper;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.opengamma.component.ComponentManager;
import com.opengamma.component.ComponentRepository;
import com.opengamma.core.position.Portfolio;
import com.opengamma.core.position.PositionSource;
import com.opengamma.core.security.SecuritySource;
import com.opengamma.engine.OptimisticMarketDataAvailabilityProvider;
import com.opengamma.engine.function.CompiledFunctionRepository;
import com.opengamma.engine.function.CompiledFunctionService;
import com.opengamma.engine.function.exclusion.FunctionExclusionGroups;
import com.opengamma.engine.marketdata.availability.MarketDataAvailabilityProvider;
import com.opengamma.engine.view.compilation.PortfolioCompiler;
import com.opengamma.master.portfolio.PortfolioMaster;
import com.opengamma.master.portfolio.PortfolioSearchRequest;
import com.opengamma.master.portfolio.PortfolioSearchResult;

public class AvailablePortfolioOutputsTest {

  private static final Logger s_logger = LoggerFactory.getLogger(AvailablePortfolioOutputsTest.class);

  private static final String SWAP_PORTFOLIO = "Multi-currency Swap Portfolio";
  private static final String MIXED_EXAMPLE_PORTFOLIO = "Swap / Swaption Portfolio";

  private ComponentRepository _repo;
  private CompiledFunctionRepository _functionRepository;
  private FunctionExclusionGroups _functionExclusionGroups;
  private MarketDataAvailabilityProvider _availabilityProvider;
  private PortfolioMaster _portfolioMaster;
  private PositionSource _positionSource;
  private SecuritySource _securitySource;
  private ExecutorService _executorService;

  @BeforeClass
  public void initialise() {
    final ComponentManager manager = new ComponentManager("test");
    manager.start("classpath:fullstack/fullstack-example-test.properties");
    _repo = manager.getRepository();
    final CompiledFunctionService cfs = _repo.getInstance(CompiledFunctionService.class, "main");
    _functionRepository = cfs.compileFunctionRepository(Instant.now());
    _functionExclusionGroups = _repo.getInstance(FunctionExclusionGroups.class, "main");
    _portfolioMaster = _repo.getInstance(PortfolioMaster.class, "central");
    _positionSource = _repo.getInstance(PositionSource.class, "combined");
    _securitySource = _repo.getInstance(SecuritySource.class, "combined");
    _availabilityProvider = new OptimisticMarketDataAvailabilityProvider();
    _executorService = Executors.newCachedThreadPool();
  }

  @AfterClass
  public void cleanup() {
    if (_repo != null) {
      _repo.stop();
    }
  }

  private Portfolio getPortfolio(final String portfolioName) {
    final PortfolioSearchRequest searchRequest = new PortfolioSearchRequest();
    searchRequest.setName(portfolioName);
    searchRequest.setIncludePositions(false);
    final PortfolioSearchResult searchResult = _portfolioMaster.search(searchRequest);
    assertNotNull(searchResult.getFirstDocument());
    // Master doesn't return a Portfolio (a ManageablePortfolio), so use the position source
    return _positionSource.getPortfolio(searchResult.getFirstDocument().getUniqueId());
  }

  private AvailableOutputs testPortfolio(final String portfolioName) {
    final long t1 = System.nanoTime();
    Portfolio portfolio = getPortfolio(portfolioName);
    final long t2 = System.nanoTime();
    portfolio = PortfolioCompiler.resolvePortfolio(portfolio, _executorService, _securitySource);
    final long t3 = System.nanoTime();
    final AvailableOutputs outputs = new AvailablePortfolioOutputs(portfolio, _functionRepository, _functionExclusionGroups, _availabilityProvider, "?");
    final long t4 = System.nanoTime();
    s_logger.info("Fetch={}ms, Resolve={}ms, Outputs={}ms", new Object[] {(t2 - t1) / 1e6, (t3 - t2) / 1e6, (t4 - t3) / 1e6 });
    s_logger.info("Outputs for {}", portfolio.getName());
    for (final AvailableOutput output : outputs.getOutputs()) {
      s_logger.info("{}", output);
    }
    s_logger.info("Portfolio node outputs for {}", portfolio.getName());
    for (final AvailableOutput output : outputs.getPortfolioNodeOutputs()) {
      s_logger.info("{}", output);
    }
    s_logger.info("Position outputs for {}", portfolio.getName());
    for (final AvailableOutput output : outputs.getPositionOutputs()) {
      s_logger.info("{}", output);
    }
    for (final String securityType : outputs.getSecurityTypes()) {
      s_logger.info("{} security outputs for {}", securityType, portfolio.getName());
      for (final AvailableOutput output : outputs.getPositionOutputs(securityType)) {
        s_logger.info("{}", output);
      }
    }
    return outputs;
  }

  private static void assertPositionOutput(final AvailableOutputs outputs, final String securityType, final String valueName) {
    for (final AvailableOutput output : outputs.getPositionOutputs(securityType)) {
      if (valueName.equals(output.getValueName())) {
        return;
      }
    }
    fail(valueName + " not available for " + securityType);
  }

  @Test
  public void testSwapPortfolio() {
    assertSwapPortfolioOutputs(testPortfolio(SWAP_PORTFOLIO));
  }

  private static void assertSwapPortfolioOutputs(final AvailableOutputs outputs) {
    assertPositionOutput(outputs, "SWAP", "Par Rate");
    assertPositionOutput(outputs, "SWAP", "Present Value");
    assertPositionOutput(outputs, "SWAP", "PV01");
    assertPositionOutput(outputs, "SWAP", "Yield Curve Node Sensitivities");
  }

  @Test
  public void testMixedExamplePortfolio() {
    assertMixedExamplePortfolioOutputs(testPortfolio(MIXED_EXAMPLE_PORTFOLIO));
  }

  private static void assertMixedExamplePortfolioOutputs(final AvailableOutputs outputs) {
    assertPositionOutput(outputs, "SWAP", "Par Rate");
    assertPositionOutput(outputs, "SWAP", "Present Value");
    assertPositionOutput(outputs, "SWAP", "PV01");
    assertPositionOutput(outputs, "SWAP", "Yield Curve Node Sensitivities");
    assertPositionOutput(outputs, "SWAPTION", "Present Value");
    //assertPositionOutput(outputs, "SWAPTION", "Present Value SABR Alpha Sensitivity"); // Don't have default cubes for non-USD instruments
    assertPositionOutput(outputs, "SWAPTION", "Yield Curve Node Sensitivities");
  }

}