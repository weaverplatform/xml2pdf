package com.weaverplatform.xml2pdf.controllers;
import com.google.common.io.Resources;

import org.apache.fop.apps.*;
import spark.Route;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URI;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ConvertController {

  public static Route convert = (req, res) -> {
    MultipartConfigElement multipartConfigElement = new MultipartConfigElement("/tmp");
    req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);

    Part file = req.raw().getPart("file");

    ZipInputStream zis = new ZipInputStream(file.getInputStream());
    ZipEntry entry = zis.getNextEntry();

    byte[] buffer = new byte[1024];

    File destDir = new File("/tmp");

    while (entry != null) {
      if(!entry.getName().contains("__MACOSX")) {
        if(entry.isDirectory()) {
          new File(destDir, entry.getName()).mkdir();
        } else {
          File newFile = new File(destDir, entry.getName());
          FileOutputStream fos = new FileOutputStream(newFile);
          int len;
          while ((len = zis.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
          }
          fos.close();
        }
      }

      entry = zis.getNextEntry();
    }

    zis.closeEntry();
    zis.close();
    HttpServletResponse raw = res.raw();

    try {
      res.header("Content-Disposition", "attachment; filename=COR-Export.pdf");
      res.header("Content-Type", MimeConstants.MIME_PDF);

      FopFactory fopFactory = FopFactory.newInstance(new File(Resources.getResource("fop.xconf").getFile()));

      Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, raw.getOutputStream());

      Source xslt = new StreamSource(new File(destDir, "stylesheet.xslt"));
      Source xml = new StreamSource(new File(destDir, "data.xml"));

      TransformerFactory factory = new net.sf.saxon.TransformerFactoryImpl();
      Transformer transformer = factory.newTransformer(xslt);

      Result result = new SAXResult(fop.getDefaultHandler());

      transformer.transform(xml, result);

    } finally {
      raw.getOutputStream().flush();
      raw.getOutputStream().close();
    }

    return raw;
  };
}
