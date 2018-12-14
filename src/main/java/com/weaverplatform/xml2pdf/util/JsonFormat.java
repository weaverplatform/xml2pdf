package com.weaverplatform.xml2pdf.util;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;

import java.lang.reflect.Type;

public class JsonFormat {
  private static Gson gson;

  static {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gson = gsonBuilder.create();
  }

  public static String toJson(Object o) {
    return gson.toJson(o);
  }

  public static void toJson(Object o, Type typeOfSrc, JsonWriter w) {
    gson.toJson(o, typeOfSrc, w);
  }

  public static <T> T fromJson(String s, Class<T> type) {
    return gson.fromJson(s, type);
  }
}