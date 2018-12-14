package com.weaverplatform.xml2pdf;

import com.weaverplatform.xml2pdf.controllers.ApplicationController;

import static com.weaverplatform.xml2pdf.util.JsonFormat.toJson;
import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

public class ApplicationControllerTest extends BaseTest {

  @Test
  public void getVersion() {
    final String VERSION = toJson(new ApplicationController.About());
    get("/").then().body(is(VERSION));
  }
}
