package com.scor.rr.domain.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RRPeriod {
    private int nb;
    private int[] events;
    private int[] seqs;
    //    private float[]quantiles;
    private double[] quantiles;
    private long[] eventDates;
}
