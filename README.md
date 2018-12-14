# XML2PDF
XML2PDF is a microservice that takes a zip containing an xml with data and an xslt stylesheet.
The resulting response is a PDF file.

## How to use it
Converting a file is fairly simple. First you need to construct a zip file with the following structure:

* data.xml
* stylesheet.xslt
* ... (whatever needs to be there for resources)

Next send the zip in a multipart POST request to the `/convert` endpoint. The resulting response will be the generated
PDF file.

## How to run it
The repository contains a docker-compose file. Running `docker-compose up` will build an run the microservice.