package com.weaverplatform.xml2pdf;

import com.google.common.io.Resources;
import static io.restassured.RestAssured.given;
import org.junit.Test;

import java.io.*;

public class ConvertControllerTest extends BaseTest {

  @Test
  public void convert() {
    File file = new File(Resources.getResource("bundle.zip").getPath());

    InputStream stream = given()
        .multiPart(file)
        .expect()
        .statusCode(200)
        .contentType("application/pdf")
        .when()
        .post("/convert")
        .then()
        .extract()
        .asInputStream();

    byte[] buffer = new byte[1024];

    try {
      File newFile = new File("./result.pdf");
      FileOutputStream fos = new FileOutputStream(newFile);
      int len;
      while ((len = stream.read(buffer)) > 0) {
        fos.write(buffer, 0, len);
      }
      fos.close();
    } catch (IOException e) {
      System.out.println(e.getStackTrace());
    }
  }
}
