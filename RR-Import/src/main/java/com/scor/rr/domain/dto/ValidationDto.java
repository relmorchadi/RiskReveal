package com.scor.rr.domain.dto;

import com.scor.rr.domain.BulkImportFile;
import com.scor.rr.domain.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationDto {


    private BulkImportFile file;
    private List<ValidationError> errors = new ArrayList<>();

    public void addErrors(List<ValidationError> errors) {
        this.errors.addAll(errors);
    }
}
