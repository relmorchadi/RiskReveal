package com.scor.rr.domain.dto;

import com.scor.rr.domain.ConvertFunction;
import com.scor.rr.domain.RlEltLoss;
import lombok.Data;

@Data
public class ELTLossnBetaFunction extends RlEltLoss {
    Double minQuantile;
    Double minLayerAtt;
    ConvertFunction convertFunction;

    public ELTLossnBetaFunction(RlEltLoss rmsELTLoss) {
        super(rmsELTLoss);
    }

    public ELTLossnBetaFunction(RlEltLoss rmsELTLoss, Double minQuantile, Double minLayerAtt, ConvertFunction convertFunction) {
        super(rmsELTLoss);
        this.minQuantile = minQuantile;
        this.minLayerAtt = minLayerAtt;
        this.convertFunction = convertFunction;
    }
}
