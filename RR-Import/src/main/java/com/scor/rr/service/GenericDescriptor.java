package com.scor.rr.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericDescriptor implements Comparable<GenericDescriptor> {
    private static final Logger log = LoggerFactory.getLogger(GenericDescriptor.class);

    private String colName;
    private String dataType;
    private String targetName;
    private int targetOrder;
    private String targetFormat;

    public int compareTo(GenericDescriptor gd) {
        return this.targetOrder - gd.targetOrder;
    }
}
