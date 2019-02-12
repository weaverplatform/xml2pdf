package com.weaverplatform.xml2pdf.controllers;
import com.weaverplatform.xml2pdf.util.Props;
import com.weaverplatform.xml2pdf.util.Transform;
import com.weaverplatform.xml2pdf.util.Zip;
import org.apache.commons.io.FileUtils;
import org.apache.fop.apps.MimeConstants;
import spark.Route;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.util.UUID;

public class ConvertController {

  public static Route convert = (req, res) -> {

    String tmpLocation = Props.get("application.tmpDir");
    File tmpDir = new File(tmpLocation, UUID.randomUUID().toString());
    tmpDir.mkdirs();

    MultipartConfigElement multipartConfigElement = new MultipartConfigElement(tmpDir.getAbsolutePath(), -1, -1, -1);
    req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
    Part file = req.raw().getPart("file");

    Zip.deflate(file.getInputStream(), tmpDir);

    HttpServletResponse rawResponse = res.raw();
    res.header("Content-Type", MimeConstants.MIME_PDF);

    try {
      Transform.toPDF(rawResponse.getOutputStream(), tmpDir);
    } finally {
      rawResponse.getOutputStream().flush();
      rawResponse.getOutputStream().close();
      FileUtils.deleteDirectory(tmpDir);
    }

    return rawResponse;
  };
}
