package com.scor.rr.domain.entities.meta.exposure;

import com.scor.rr.domain.entities.references.omega.BinFile;
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
