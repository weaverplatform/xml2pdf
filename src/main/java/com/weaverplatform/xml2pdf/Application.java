package com.weaverplatform.xml2pdf;

import com.weaverplatform.xml2pdf.controllers.ApplicationController;
import com.weaverplatform.xml2pdf.controllers.ConvertController;
import com.weaverplatform.xml2pdf.util.Props;

import static spark.Spark.*;

public class Application {
  final int PORT = Props.getInt("PORT", "application.port");

  public Application() {
    port(PORT);

    get("/", ApplicationController.about);
    post("/convert", ConvertController.convert);

    get("*", ApplicationController.notFound);
  }

  public static void main(String[] args) {
    new Application();
  }
}
