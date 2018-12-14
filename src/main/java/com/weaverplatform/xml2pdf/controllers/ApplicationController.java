package com.weaverplatform.xml2pdf.controllers;

import com.weaverplatform.xml2pdf.util.Props;
import static com.weaverplatform.xml2pdf.util.JsonFormat.toJson;

import spark.Route;

public class ApplicationController {
  public static Route about = (req, res) -> toJson(new About());

  public static Route notFound = (req, res) -> {
    res.status(404);
    return "";
  };

  public static class About {
    private String name, version;

    public About() {
      this.name = Props.get("application.name");
      this.version = Props.get("application.version");
    }
  }
}
