package com.weaverplatform.xml2pdf.util;

import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class Props extends Properties {

  private static Props instance;
  private static Logger logger = LoggerFactory.getLogger(Props.class);

  protected Props() {
    try {
      load(Resources.getResource("properties/main.properties").openStream());
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  private static Props getInstance() {
    if(instance == null) {
      instance = new Props();
    }
    return instance;
  }

  public static String get(String propKey){
    return getInstance().getProperty(propKey);
  }

  public static String get(String envKey, String propKey){
    if (System.getenv(envKey) != null){
      return System.getenv(envKey);
    }
    else {
      return get(propKey);
    }
  }

  public static Integer getInt(String envKey, String propKey) {
    return Integer.valueOf(get(envKey, propKey));
  }
}