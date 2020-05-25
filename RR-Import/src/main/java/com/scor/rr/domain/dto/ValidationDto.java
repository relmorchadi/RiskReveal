package com.scor.rr.domain.dto;

import com.scor.rr.domain.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationDto {

    private Map<Integer, List<?>> data = new HashMap<>();
    private List<ValidationError> errors = new ArrayList<>();

    public void addErrors(List<ValidationError> errors) {
        this.errors.addAll(errors);
    }

    public void addData(Integer index, List<?> data) {
        this.data.put(index, data);
    }
}
