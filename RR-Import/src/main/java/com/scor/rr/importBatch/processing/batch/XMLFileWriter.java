package com.scor.rr.importBatch.processing.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class XMLFileWriter extends BaseFileWriter implements XMLWriter {
    private static final Logger log= LoggerFactory.getLogger(XMLFileWriter.class);

    public XMLFileWriter() {
    }

    public XMLFileWriter(String filePath, String fileExtension) {
        super(filePath, fileExtension);
    }

    @Override
    public boolean write(String xmlSource, File output) {
        try {
            writePrv(xmlSource, output);
        } catch (TransformerException e) {
            log.error("TransformerException {}", e);
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            log.error("ParserConfigurationException {}", e);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("IOException {}", e);
            e.printStackTrace();
        } catch (SAXException e) {
            log.error("SAXException {}", e);
            e.printStackTrace();
        }
        return true;
    }

    private void writePrv(String xmlSource, File output) throws TransformerException, ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlSource)));

        // Write the parsed document to an xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        StreamResult result =  new StreamResult(output);
        transformer.transform(source, result);

    }

    @Override
    public boolean write(Object toMarshall, String type, String rp, String fp, String ccy, String model, String sfx, String fileExtension) {
        return false;
    }
}
