package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExposureManagerData {

    private String country;
    private String admin1;
    private BigDecimal totalTiv;
    private BigDecimal expectedTiv;
    private BigDecimal tivDiff;

    private Map<String, Object> regionPerils;

    public void addToRegionPerils(String key, Double value) {
        if (regionPerils == null)
            regionPerils = new HashMap<>();
        regionPerils.put(key, value);
    }
}
