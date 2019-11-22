package com.scor.rr.service.calculation;

import com.scor.rr.domain.ConvertFunction;

public interface ConvertFunctionFactory {
    ConvertFunction buildFunction(Double meanLoss, Double stdDevC, Double stdDevU, Double stdDevUSq, Double exposureValue, Double minLayerAtt);
}
