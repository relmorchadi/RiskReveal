package com.scor.rr.domain.adjustments;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by U002629 on 03/03/2016.
 */
public class AdjustmentDefinition {
    final String type;

    public AdjustmentDefinition(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String label(){return "";}

    public static boolean equals(AdjustmentDefinition da, AdjustmentDefinition db) {
        if (da == null && db == null) {
            throw new IllegalStateException();
        }
        if (!StringUtils.equalsIgnoreCase(da == null ? null : da.getType(), db == null ? null : db.getType())) {
            return false;
        }
        if (da instanceof DummyAdjustmentDefinition) {
            return true;
        }
        if (da instanceof LinearAdjustmentDefinition) {
            return Double.compare(((LinearAdjustmentDefinition) da).getAmount(), ((LinearAdjustmentDefinition) db).getAmount()) == 0;
        }
        return false;
    }

    public static boolean isLinear(AdjustmentDefinition ad) {
        return StringUtils.equalsIgnoreCase(ad.getType(), LinearAdjustmentDefinition.type);
    }
}
