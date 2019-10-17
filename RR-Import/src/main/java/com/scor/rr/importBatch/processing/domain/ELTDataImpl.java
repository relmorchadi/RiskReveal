package com.scor.rr.importBatch.processing.domain;

import com.scor.rr.importBatch.processing.domain.loss.EventLoss;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by U002629 on 27/02/2015.
 */
public class ELTDataImpl implements ELTData {

    private final Map<String, ELTLoss> lossesByRP;

    public ELTDataImpl() {
        lossesByRP = new TreeMap<String, ELTLoss>();
    }

    /* (non-Javadoc)
		 * @see ELTData#addRP(java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
		 */
    @Override
		public void addRP(Integer analysisId, String r, String p, String curr, String fp, String dlm){
        lossesByRP.put(r+p, new ELTLoss(r, p, curr, analysisId, fp, dlm));
    }

    /* (non-Javadoc)
		 * @see ELTData#addLosData(java.lang.String, ELTLoss)
		 */
    @Override
		public void addLosData(String rp, ELTLoss data){
        lossesByRP.put(rp, data);
    }

    /* (non-Javadoc)
		 * @see ELTData#getRegionPerils()
		 */
    @Override
		public Set<String> getRegionPerils(){
        return lossesByRP.keySet();
    }

    /* (non-Javadoc)
		 * @see ELTData#addEvent(java.lang.String, com.scor.almf.ihub.processing.domain.EventLoss)
		 */
    @Override
		public void addEvent(String rp, EventLoss e){
        lossesByRP.get(rp).addLoss(e);
    }

    /* (non-Javadoc)
		 * @see ELTData#getEvents(java.lang.String)
		 */
    @Override
		public List<EventLoss> getEvents(String rp) {
        return  lossesByRP.get(rp).getEventLosses();
    }

    /* (non-Javadoc)
		 * @see ELTData#getLossDataForRp(java.lang.String)
		 */
    @Override
		public ELTLoss getLossDataForRp(String rp){
        return lossesByRP.get(rp);
    }

    @Override
    public void clearLossDataForRp(String rp){
        lossesByRP.remove(rp);
    }

    /* (non-Javadoc)
		 * @see ELTData#reset()
		 */
    @Override
		public void reset(){
        lossesByRP.clear();
    }

}
