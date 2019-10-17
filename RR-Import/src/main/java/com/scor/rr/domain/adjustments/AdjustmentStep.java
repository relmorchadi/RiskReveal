package com.scor.rr.domain.adjustments;

import com.scor.rr.domain.utils.plt.PLT;
import lombok.Data;

import java.util.LinkedHashMap;

/**
 * Created by U002629 on 03/03/2016.
 */
@Data
public class AdjustmentStep {

    public enum AdjustmentClass{PURE(0),BASELINE(1), DEFAULT(2), ANALYST(3), CLIENT(4), FINAL(5);
        final int zoneId;
        AdjustmentClass(int zoneId) {
            this.zoneId = zoneId;
        }

        public int getZoneId() {
            return zoneId;
        }

        public static AdjustmentClass getFromZoneId(int zoneId){
            switch (zoneId){
                case 0: return PURE;
                case 1: return BASELINE;
                case 2: return DEFAULT;
                case 3: return ANALYST;
                case 4: return CLIENT;
                case 5: return FINAL;
                default:return null;
            }
        }
    }

    String id;
    String name;
    String description;
    String parentId;
    LinkedHashMap<String, ?> children;
    PLT ajustedPLT;
    AdjustmentDefinition adjustmentDefinition;
    AdjustmentClass adjustmentClass;
    PLTDataState dataState;

    public void addChild(AdjustmentStep child){
        LinkedHashMap<String, AdjustmentStep> c = (LinkedHashMap<String, AdjustmentStep>) children;
        child.setParentId(id);
        c.put(child.getId(), child);
    }

    public AdjustmentStep() {
        children = new LinkedHashMap<String, AdjustmentStep>();
    }
}
