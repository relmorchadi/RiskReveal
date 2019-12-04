package com.scor.rr.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    public static List<String> parseXMLForModelingOptions(String xml) throws DocumentException {

        List<String> options = new ArrayList<>();
        SAXReader reader = new SAXReader();
        InputStream is = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        Document document = reader.read(is);

        Element root = document.getRootElement();

        List nodes = document.selectNodes("//RmsDlmProfile/ModellingOptions/*");

        for (Iterator iter = nodes.iterator(); iter.hasNext(); ) {
            Node node = (Node) iter.next();
            Element element = (Element) node;
            String option = element.attributeValue("Code");
            options.add(option);
            log.info("path {}, name {}, text {}, value {}, option", node.getPath(), node.getName(), node.getText(), node.getStringValue(), option);
        }

        return options;
    }

    public static void setAttribute(Object obj, String fieldName, Integer value){
        PropertyDescriptor pd;
        try {
            pd = new PropertyDescriptor(fieldName, obj.getClass());
            pd.getWriteMethod().invoke(obj, value);
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
