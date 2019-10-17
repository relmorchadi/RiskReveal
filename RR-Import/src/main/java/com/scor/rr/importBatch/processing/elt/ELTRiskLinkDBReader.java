package com.scor.rr.importBatch.processing.elt;

import com.scor.rr.importBatch.processing.domain.loss.EventLoss;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by U002629 on 19/02/2015.
 */
public class ELTRiskLinkDBReader implements ELTReader {
    @Override
    public List<EventLoss> readELTs(String[] elts) {
        return null;
    }

    @Override
    public Map<Integer, EventLoss> aggregateELTsAndBuildBetas(String[] elts) {
        return null;
    }

    @Override
    public Map<Integer, EventLoss> aggregateELTsAndBuildBetas(List<EventLoss> events) {
        final Map<Integer, EventLoss> eventLossMap = Collections.synchronizedMap(new HashMap<Integer, EventLoss>(events.size()));

        for (EventLoss elt : events) {
            EventLoss myLoss = eventLossMap.get(elt.getEventId());
            if (myLoss == null) {
                eventLossMap.put(elt.getEventId(), elt);
            } else {
                myLoss.addLoss(elt);
            }
        }
        for (EventLoss eventLoss : eventLossMap.values()) {
            eventLoss.buildFunction(null);
        }
        return eventLossMap;
    }
}
