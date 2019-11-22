package com.scor.rr.domain.model;

import com.scor.rr.domain.dto.BinFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExposureSummaryExtractFile {

    private BinFile extractFile;
    private String extractFileType;
}