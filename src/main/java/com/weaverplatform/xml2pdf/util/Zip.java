package com.weaverplatform.xml2pdf.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Zip {

  public static void deflate(InputStream input, File targetDir) throws IOException {
    ZipInputStream zis = new ZipInputStream(input);
    ZipEntry entry = zis.getNextEntry();

    byte[] buffer = new byte[1024];

    while (entry != null) {
      if(entry.isDirectory()) {
        new File(targetDir, entry.getName()).mkdir();
      } else {
        File newFile = new File(targetDir, entry.getName());
        FileOutputStream fos = new FileOutputStream(newFile);
        int len;
        while ((len = zis.read(buffer)) > 0) {
          fos.write(buffer, 0, len);
        }
        fos.close();
      }

      entry = zis.getNextEntry();
    }

    zis.closeEntry();
    zis.close();
  }
}
