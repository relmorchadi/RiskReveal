package com.scor.rr.util;

import javax.persistence.AttributeConverter;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

public class ListStringConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> list) {
        String jsonString;
        if (list == null)
            return null;
        else
            jsonString = list.toString();
            return jsonString;
    }

    @Override
    public List<String> convertToEntityAttribute(String jsonString) {
        if (jsonString == null || jsonString.isEmpty())
            return null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            List<String> list = Arrays.asList(mapper.readValue(jsonString, String[].class));
            return list;
        } catch (IOException e) {
            return null;
        }
    }

}
