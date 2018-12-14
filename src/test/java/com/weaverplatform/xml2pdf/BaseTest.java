package com.weaverplatform.xml2pdf;

import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class BaseTest {

  static Application application;

  @BeforeClass
  public static void setup() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = 2295;

    if(application == null) {
      application = new Application();
    }
  }
}
