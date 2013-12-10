/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.integration.copier.snapshot.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opengamma.OpenGammaRuntimeException;
import com.opengamma.core.marketdatasnapshot.CurveKey;
import com.opengamma.core.marketdatasnapshot.CurveSnapshot;
import com.opengamma.core.marketdatasnapshot.UnstructuredMarketDataSnapshot;
import com.opengamma.core.marketdatasnapshot.VolatilitySurfaceKey;
import com.opengamma.core.marketdatasnapshot.VolatilitySurfaceSnapshot;
import com.opengamma.core.marketdatasnapshot.YieldCurveKey;
import com.opengamma.core.marketdatasnapshot.YieldCurveSnapshot;
import com.opengamma.core.marketdatasnapshot.impl.ManageableUnstructuredMarketDataSnapshot;
import com.opengamma.id.ExternalIdBundle;
import com.opengamma.integration.copier.sheet.reader.XlsSheetReader;
import com.opengamma.integration.copier.snapshot.SnapshotType;
import com.opengamma.integration.tool.marketdata.MarketDataSnapshotToolUtils;
import com.opengamma.util.tuple.ObjectsPair;

/**
 * Reads a snapshot from an imported file
 */
public class XlsSnapshotReader implements SnapshotReader{
  private static final Logger s_logger = LoggerFactory.getLogger(XlsSnapshotReader.class);

  private Map<CurveKey, CurveSnapshot> _curves;
  private UnstructuredMarketDataSnapshot _global;
  private Map<VolatilitySurfaceKey, VolatilitySurfaceSnapshot> _surface;
  private Map<YieldCurveKey, YieldCurveSnapshot> _yieldCurve;
  private String _name;
  private String _basisName;
  private XlsSheetReader _nameSheet;
  private XlsSheetReader _globalsSheet;
  private Workbook _workbook;
  private InputStream _fileInputStream;
  private final String valueObject = "Market_Value";

  public XlsSnapshotReader(String filename) {
    _fileInputStream = openFile(filename);
    _workbook = getWorkbook(_fileInputStream);
    buildNameData();
    buildGlobalData();

  }

  private Workbook getWorkbook(InputStream inputStream) {
    try {
      return new HSSFWorkbook(inputStream);
    } catch (IOException ex) {
      throw new OpenGammaRuntimeException("Error opening Excel workbook: " + ex.getMessage());
    }
  }

  private ExternalIdBundle createExternalIdBundle(String idBundle) {
    Iterable<String> iterable = Arrays.asList(idBundle.split("\\|"));
    s_logger.warn("ID Bundle {}", iterable.toString());
    return ExternalIdBundle.parse(iterable);
  }

  private void buildGlobalData() {
    _globalsSheet = new XlsSheetReader(_workbook, SnapshotType.GLOBAL_VALUES.get());
    Map<String, ObjectsPair<String, String>> globalMap = _globalsSheet.readKeyPairBlock(_globalsSheet.getCurrentRowIndex(), 0);
    ManageableUnstructuredMarketDataSnapshot globalBuilder = new ManageableUnstructuredMarketDataSnapshot();
    for (Map.Entry<String, ObjectsPair<String, String>> entry : globalMap.entrySet()) {
      globalBuilder.putValue(createExternalIdBundle(entry.getKey()),
                             valueObject,
                             MarketDataSnapshotToolUtils.createValueSnapshot(entry.getValue().getFirst(),
                                                                             entry.getValue().getSecond()));
    }
    _global = globalBuilder;
  }

  private void buildNameData() {
    _nameSheet = new XlsSheetReader(_workbook, SnapshotType.NAME.get());
    Map<String, String> nameMap = _nameSheet.readKeyValueBlock(_nameSheet.getCurrentRowIndex(), 0);
    nameMap.putAll(_nameSheet.readKeyValueBlock(_nameSheet.getCurrentRowIndex(), 0));
    _name = nameMap.get(SnapshotType.NAME.get());
    _basisName = nameMap.get(SnapshotType.BASIS_NAME.get());
  }

  protected static InputStream openFile(String filename) {
    // Open input file for reading
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(filename);
    } catch (FileNotFoundException ex) {
      throw new OpenGammaRuntimeException("Could not open file " + filename + " for reading, exiting immediately.");
    }

    return fileInputStream;
  }

  @Override
  public Map<CurveKey, CurveSnapshot> readCurves() {
    return _curves;
  }

  @Override
  public UnstructuredMarketDataSnapshot readGlobalValues() {
    return _global;
  }

  @Override
  public Map<VolatilitySurfaceKey, VolatilitySurfaceSnapshot> readVolatilitySurfaces() {
    return _surface;
  }

  @Override
  public Map<YieldCurveKey, YieldCurveSnapshot> readYieldCurves() {
    return _yieldCurve;
  }

  @Override
  public void close() {
    //TODO
  }

  @Override
  public String getName() {
    return _name;
  }

  @Override
  public String getBasisViewName() {
    return _basisName;
  }
}
