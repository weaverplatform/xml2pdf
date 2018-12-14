package com.weaverplatform.xml2pdf.util;

import net.sf.saxon.TransformerFactoryImpl;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.OutputStream;

public class Transform {
  public static void toPDF(OutputStream targetStream, File tmpDir) throws FOPException, TransformerException {
    FopFactory fopFactory = FopFactory.newInstance(tmpDir.toURI());

    Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, targetStream);

    Source xslt = new StreamSource(new File(tmpDir, "stylesheet.xslt"));
    Source xml = new StreamSource(new File(tmpDir, "data.xml"));

    TransformerFactory factory = new TransformerFactoryImpl();
    Transformer transformer = factory.newTransformer(xslt);

    Result result = new SAXResult(fop.getDefaultHandler());

    transformer.transform(xml, result);
  }
}
