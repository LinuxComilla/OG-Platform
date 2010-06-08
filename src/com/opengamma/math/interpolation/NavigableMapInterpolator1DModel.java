/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.math.interpolation;

import java.util.Iterator;
import java.util.NavigableMap;

import com.opengamma.util.ArgumentChecker;

/**
 * An implementation of {@link Interpolator1DModel} backed by a
 * {@link NavigableMap}.
 * 
 */
public class NavigableMapInterpolator1DModel implements Interpolator1DModel {
  private final NavigableMap<Double, Double> _backingMap;
  
  public NavigableMapInterpolator1DModel(NavigableMap<Double, Double> backingMap) {
    ArgumentChecker.notNull(backingMap, "Backing map");
    _backingMap = backingMap;
  }

  @Override
  public Double getLowerBoundKey(Double value) {
    return _backingMap.floorKey(value);
  }

  @Override
  public int getLowerBoundIndex(Double value) {
    final Double lower = getLowerBoundKey(value);
    int i = 0;
    final Iterator<Double> iter = _backingMap.keySet().iterator();
    Double key = iter.next();
    while (!key.equals(lower)) {
      key = iter.next();
      i++;
    }
    return i;
  }

  @Override
  public Double get(Double key) {
    return _backingMap.get(key);
  }

  @Override
  public Double lastKey() {
    return _backingMap.lastKey();
  }

  @Override
  public Double lastValue() {
    return _backingMap.lastEntry().getValue();
  }

  @Override
  public Double higherKey(Double key) {
    return _backingMap.higherKey(key);
  }

  @Override
  public Double higherValue(Double key) {
    return _backingMap.higherEntry(key).getValue();
  }

  @Override
  public int size() {
    return _backingMap.size();
  }

  @Override
  public double[] getKeys() {
    double[] result = new double[size()];
    int i = 0;
    for (Double d : _backingMap.keySet()) {
      result[i++] = d;
    }
    assert i == size();
    return result;
  }

  @Override
  public double[] getValues() {
    double[] result = new double[size()];
    int i = 0;
    for (Double d : _backingMap.values()) {
      result[i++] = d;
    }
    assert i == size();
    return result;
  }

  @Override
  public Double firstKey() {
    return _backingMap.firstKey();
  }

  @Override
  public Double firstValue() {
    return _backingMap.firstEntry().getValue();
  }

  @Override
  public boolean containsKey(Double key) {
    return _backingMap.containsKey(key);
  }

}
