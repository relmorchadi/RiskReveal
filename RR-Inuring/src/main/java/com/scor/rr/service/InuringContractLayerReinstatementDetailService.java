package com.scor.rr.service;


import com.scor.rr.entity.InuringContractLayer;
import com.scor.rr.entity.InuringContractLayerReinstatementDetail;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.InuringContractLayerNotFoundException;
import com.scor.rr.repository.InuringContractLayerReinstatementDetailRepository;
import com.scor.rr.repository.InuringContractLayerRepository;
import com.scor.rr.request.InuringContractLayerReinstatementDetailCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class InuringContractLayerReinstatementDetailService {

    @Autowired
    private InuringContractLayerReinstatementDetailRepository inuringContractLayerReinstatementDetailRepository;

    @Autowired
    private InuringContractLayerRepository inuringContractLayerRepository;

    public void createInuringContractLayerReinstatementDetail(InuringContractLayerReinstatementDetailCreationRequest request) throws RRException {

        InuringContractLayer inuringContractLayer = inuringContractLayerRepository.findByInuringContractLayerId(request.getInuringContractLayerId());
        if(inuringContractLayer == null) throw new InuringContractLayerNotFoundException(request.getInuringContractLayerId());

        InuringContractLayerReinstatementDetail inuringContractLayerReinstatementDetail = new InuringContractLayerReinstatementDetail(
                request.getInuringContractLayerId(),
                request.getReinstatementsRank(),
                request.getReinstatementsNumber(),
                request.getReinstatatementsCharge()
        );
        inuringContractLayerReinstatementDetailRepository.save(inuringContractLayerReinstatementDetail);
    }

    public void deleteInuringContractLayerReinstatementDetailById(int inuringContractLayerReinstatementDetailId  ){
        inuringContractLayerReinstatementDetailRepository.deleteByInuringContractLayerReinstatementDetailId( inuringContractLayerReinstatementDetailId);
    }

}
