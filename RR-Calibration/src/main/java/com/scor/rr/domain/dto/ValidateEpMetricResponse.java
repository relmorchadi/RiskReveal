package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ValidateEpMetricResponse {
    @NonNull Integer lowerBound;
    @NonNull Integer upperBound;
    @NonNull Boolean isValid;
}
