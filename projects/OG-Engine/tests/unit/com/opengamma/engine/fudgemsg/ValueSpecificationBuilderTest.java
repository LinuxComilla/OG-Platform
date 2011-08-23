/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.engine.fudgemsg;

import org.testng.annotations.Test;

import com.opengamma.engine.ComputationTargetSpecification;
import com.opengamma.engine.value.ValueProperties;
import com.opengamma.engine.value.ValuePropertyNames;
import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.util.test.AbstractBuilderTestCase;

@Test
public class ValueSpecificationBuilderTest extends AbstractBuilderTestCase {

  public void testEncoding() {
    assertEncodeDecodeCycle(ValueSpecification.class, new ValueSpecification("requirement", new ComputationTargetSpecification("Foo"), ValueProperties.with(ValuePropertyNames.FUNCTION, "Bar").get()));
  }

}
