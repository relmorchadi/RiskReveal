package com.scor.rr.importBatch.processing.domain;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by U002629 on 04/03/2015.
 */
@Service
public class PLTDataImpl implements PLTData {

    private final Map<String, PLTLoss> lossesByRP;

    public PLTDataImpl() {
        lossesByRP = new TreeMap<String, PLTLoss>();
    }

    /* (non-Javadoc)
		 * @see PLTData#addPeriod(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
		 */
    @Override
		public synchronized void addPeriod(String rp, String region, String peril, String currency, String financialPerspective){
        lossesByRP.put(rp, new PLTLoss(region, peril, currency, financialPerspective));
    }

    /* (non-Javadoc)
		 * @see PLTData#getRegionPerils()
		 */
    @Override
		public synchronized Set<String> getRegionPerils(){
        return lossesByRP.keySet();
    }

    /* (non-Javadoc)
		 * @see PLTData#getLossDataForRP(java.lang.String)
		 */
    @Override
    public synchronized PLTLoss getLossDataForRP(String rp) {
        return lossesByRP.get(rp);
    }

    @Override
    public synchronized void initLossDataForRP(String rp) {
        lossesByRP.put(rp, new PLTLoss());
    }

    /* (non-Javadoc)
		 * @see PLTData#reset()
		 */
    @Override
    public synchronized void reset(){
        lossesByRP.clear();
    }

    @Override
    public synchronized void sortLosses(){
        for (PLTLoss pltLoss : lossesByRP.values()) {
            pltLoss.sortPeriods();
        }
    }

    @Override
    public synchronized void sortLosses(String rp){
        lossesByRP.get(rp).sortPeriods();
    }

    @Override
    public synchronized void clearLossesForRP(String rp){
        lossesByRP.remove(rp);
    }
}
