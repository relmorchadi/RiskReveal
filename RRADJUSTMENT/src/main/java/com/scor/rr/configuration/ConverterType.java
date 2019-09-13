package com.scor.rr.configuration;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ConverterType<T> {
    public T value;
    private final Class<T> clazz;

    public ConverterType(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @SuppressWarnings("unchecked")
    public void convert(String input) {
        if (clazz.isAssignableFrom(String.class)) {
            value = (T) input;
        } else if (clazz.isAssignableFrom(Integer.class)) {
            value = (T) Integer.valueOf(input);
        } else if (clazz.isAssignableFrom(Double.class)) {
            value = (T) Double.valueOf(input);
        } else if(clazz.isAssignableFrom(Timestamp.class)){
            if(DateConverter.parse(input) != null ) {
                SimpleDateFormat sdf = new SimpleDateFormat(DateConverter.parse(input));
                try {
                    value = (T) new Timestamp(sdf.parse(input).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new IllegalArgumentException("Bad type.");
        }
    }
}
