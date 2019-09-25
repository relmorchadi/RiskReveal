package com.scor.rr.configuration;

import com.scor.rr.domain.MetadataHeaderSectionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ConverterType<T> {

    private static final Logger log = LoggerFactory.getLogger(ConverterType.class);
    public T value;
    private final Class<T> clazz;

    public ConverterType(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @SuppressWarnings("unchecked")
    public void convert(String input, MetadataHeaderSectionEntity headerSectionEntity) {
        if (clazz.isAssignableFrom(String.class)) {
            value = (T) input;
        } else if (clazz.isAssignableFrom(Integer.class)) {
            value = (T) Integer.valueOf(input);
        } else if (clazz.isAssignableFrom(Double.class)) {
            value = (T) Double.valueOf(input);
        } else if(clazz.isAssignableFrom(Timestamp.class)){
            String dateFromat = DateConverter.parse(input);
            if( dateFromat != null ) {
                if (headerSectionEntity.getFormat() != null) {
                    if (headerSectionEntity.getFormat().equals(DateConverter.parse(input))) {
                        SimpleDateFormat sdf = new SimpleDateFormat(DateConverter.parse(input));
                        try {
                            value = (T) new Timestamp(sdf.parse(input).getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        log.info("Bad format of type expected "+headerSectionEntity.getFormat()+" -> type  "+dateFromat);
                        value = null;
                    }
                }
            } else {
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
