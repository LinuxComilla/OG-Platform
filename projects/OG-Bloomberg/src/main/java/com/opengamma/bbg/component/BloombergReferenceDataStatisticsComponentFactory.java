/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.bbg.component;

import java.util.LinkedHashMap;
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

import com.opengamma.bbg.referencedata.statistics.BloombergReferenceDataStatistics;
import com.opengamma.bbg.referencedata.statistics.DailyBloombergReferenceDataStatistics;
import com.opengamma.bbg.referencedata.statistics.DailyBloombergReferenceDataStatisticsMBean;
import com.opengamma.component.ComponentInfo;
import com.opengamma.component.ComponentRepository;
import com.opengamma.component.factory.AbstractComponentFactory;

/**
 * Component factory for the Bloomberg Reference Data Statistics.
 */
@BeanDefinition
public class BloombergReferenceDataStatisticsComponentFactory extends AbstractComponentFactory {
  /**
   * The classifier that the factory should publish under.
   */
  @PropertyDefinition(validate = "notNull")
  private String _classifier;

  @Override
  public void init(ComponentRepository repo, LinkedHashMap<String, String> configuration) throws Exception {
    DailyBloombergReferenceDataStatistics stats = new DailyBloombergReferenceDataStatistics();
    final ComponentInfo info = new ComponentInfo(BloombergReferenceDataStatistics.class, getClassifier());
    repo.registerComponent(info, stats);
    repo.registerMBean(new DailyBloombergReferenceDataStatisticsMBean(stats));
  }
  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code BloombergReferenceDataStatisticsComponentFactory}.
   * @return the meta-bean, not null
   */
  public static BloombergReferenceDataStatisticsComponentFactory.Meta meta() {
    return BloombergReferenceDataStatisticsComponentFactory.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(BloombergReferenceDataStatisticsComponentFactory.Meta.INSTANCE);
  }

  @Override
  public BloombergReferenceDataStatisticsComponentFactory.Meta metaBean() {
    return BloombergReferenceDataStatisticsComponentFactory.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the classifier that the factory should publish under.
   * @return the value of the property, not null
   */
  public String getClassifier() {
    return _classifier;
  }

  /**
   * Sets the classifier that the factory should publish under.
   * @param classifier  the new value of the property, not null
   */
  public void setClassifier(String classifier) {
    JodaBeanUtils.notNull(classifier, "classifier");
    this._classifier = classifier;
  }

  /**
   * Gets the the {@code classifier} property.
   * @return the property, not null
   */
  public final Property<String> classifier() {
    return metaBean().classifier().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public BloombergReferenceDataStatisticsComponentFactory clone() {
    return (BloombergReferenceDataStatisticsComponentFactory) super.clone();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      BloombergReferenceDataStatisticsComponentFactory other = (BloombergReferenceDataStatisticsComponentFactory) obj;
      return JodaBeanUtils.equal(getClassifier(), other.getClassifier()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getClassifier());
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("BloombergReferenceDataStatisticsComponentFactory{");
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
    buf.append("classifier").append('=').append(JodaBeanUtils.toString(getClassifier())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code BloombergReferenceDataStatisticsComponentFactory}.
   */
  public static class Meta extends AbstractComponentFactory.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code classifier} property.
     */
    private final MetaProperty<String> _classifier = DirectMetaProperty.ofReadWrite(
        this, "classifier", BloombergReferenceDataStatisticsComponentFactory.class, String.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "classifier");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -281470431:  // classifier
          return _classifier;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends BloombergReferenceDataStatisticsComponentFactory> builder() {
      return new DirectBeanBuilder<BloombergReferenceDataStatisticsComponentFactory>(new BloombergReferenceDataStatisticsComponentFactory());
    }

    @Override
    public Class<? extends BloombergReferenceDataStatisticsComponentFactory> beanType() {
      return BloombergReferenceDataStatisticsComponentFactory.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code classifier} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> classifier() {
      return _classifier;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -281470431:  // classifier
          return ((BloombergReferenceDataStatisticsComponentFactory) bean).getClassifier();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -281470431:  // classifier
          ((BloombergReferenceDataStatisticsComponentFactory) bean).setClassifier((String) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((BloombergReferenceDataStatisticsComponentFactory) bean)._classifier, "classifier");
      super.validate(bean);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}