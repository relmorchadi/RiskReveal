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
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

public class Utils {

    public static final String ACC_HEADER = "CatAnalysisRequestID|DivisionNumber|ContractInceptionDate|PracticalCompletionDate|RegionPerilCode|CatDeductible|CatLimit";
    public static final String LOC_FW_HEADER = "CatAnalysisRequestID|DivisionNumber|RegionPerilCode|LocationID|ConstructionScheme|ConstructionCode|ConstructionLabel|OccupancyScheme|OccupancyCode|OccupancyLabel|WindStormZone|ExposedValue|ExpectedLoss|CurrencyCode|BuildingTIV|ContentTIV|BITIV|TIV|AccuracyLevel|PrimaryFloodZone|NeighboringFloodZones|AnnualProbabilityofFlooding|DistCoast";
    public static final String LOC_HEADER = "CatAnalysisRequestID|DivisionNumber|RegionPerilCode|LocationID|LocationName|StreetAddress|CityName|County|State|PostalZipCode|ConstructionScheme|ConstructionCode|ConstructionLabel|OccupancyScheme|OccupancyCode|OccupancyLabel|WindStormZone|ExposedValue|ExpectedLoss|CurrencyCode|BuildingTIV|ContentTIV|BITIV|TIV|AccuracyLevel|PrimaryFloodZone|NeighboringFloodZones|AnnualProbabilityofFlooding|DistCoast|Elevation|SoilTypeName|SoilMatchLevel|LiquefactionName|LiquefactionMatchLevel|LandslideName|LandslideMatchLevel";
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

    public static void setAttribute(Object obj, String fieldName, Double value) {
        PropertyDescriptor pd;
        try {
            pd = new PropertyDescriptor(fieldName, obj.getClass());
            pd.getWriteMethod().invoke(obj, value);
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    }

    public static List applyOffsetSizeToList(List source, int offset, int size) {
        List result = new ArrayList<>();
        if (offset < 0 || size < 0)
            throw new RuntimeException("Invalid Offset / Size params");
        for (int index : IntStream.range(offset, offset + size).toArray()) {
            if (index >= source.size() - 1)
                break;
            result.add(source.get(index));
        }
        return result;
    }
}
