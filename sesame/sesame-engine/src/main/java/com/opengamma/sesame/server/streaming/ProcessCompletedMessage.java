/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.server.streaming;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.base.Function;

/**
 * Immutable bean indicating that a view has completed all processing.
 */
@BeanDefinition(builderScope = "private")
public final class ProcessCompletedMessage implements Function<StreamingClientResultListener, Object>, ImmutableBean {

  /**
   * The instance of the process completed message.
   */
  public static ProcessCompletedMessage INSTANCE = new ProcessCompletedMessage();

  @Override
  public Object apply(StreamingClientResultListener input) {
    input.processCompleted();
    return null;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ProcessCompletedMessage}.
   * @return the meta-bean, not null
   */
  public static ProcessCompletedMessage.Meta meta() {
    return ProcessCompletedMessage.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(ProcessCompletedMessage.Meta.INSTANCE);
  }

  private ProcessCompletedMessage() {
  }

  @Override
  public ProcessCompletedMessage.Meta metaBean() {
    return ProcessCompletedMessage.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(32);
    buf.append("ProcessCompletedMessage{");
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ProcessCompletedMessage}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null);

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    public BeanBuilder<? extends ProcessCompletedMessage> builder() {
      return new ProcessCompletedMessage.Builder();
    }

    @Override
    public Class<? extends ProcessCompletedMessage> beanType() {
      return ProcessCompletedMessage.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code ProcessCompletedMessage}.
   */
  private static final class Builder extends DirectFieldsBeanBuilder<ProcessCompletedMessage> {

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      throw new NoSuchElementException("Unknown property: " + propertyName);
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      throw new NoSuchElementException("Unknown property: " + propertyName);
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public ProcessCompletedMessage build() {
      return new ProcessCompletedMessage();
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      return "ProcessCompletedMessage.Builder{}";
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
