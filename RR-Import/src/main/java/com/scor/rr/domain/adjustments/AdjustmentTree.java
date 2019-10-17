package com.scor.rr.domain.adjustments;

import com.scor.rr.domain.utils.plt.PLT;
import lombok.Data;

/**
 * Created by U002629 on 03/03/2016.
 */
@Data
public class AdjustmentTree {
    String carId;
    Integer division;
    String regionPeril;
    String periodBasis;
    String catObjectGroup;
    String name;
    String description;
    AdjustmentStep start;
    PLT purePLT;
}
