package com.scor.rr.service;

import com.scor.rr.entity.InuringContractLayer;
import com.scor.rr.entity.InuringContractLayerPerilLimit;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.InuringContractLayerNotFoundException;
import com.scor.rr.repository.InuringContractLayerPerilLimitRepository;
import com.scor.rr.repository.InuringContractLayerRepository;
import com.scor.rr.request.InuringContractLayerPerilLimitCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class InuringContractLayerPerilLimitService {

    @Autowired
    private InuringContractLayerRepository inuringContractLayerRepository;

    @Autowired
    private InuringContractLayerPerilLimitRepository inuringContractLayerPerilLimitRepository;

    public void createInuringContractLayerPerilLimit(InuringContractLayerPerilLimitCreationRequest request) throws RRException{
        InuringContractLayer inuringContractLayer = inuringContractLayerRepository.findByInuringContractLayerId(request.getInuringContractLayerId());
        if(inuringContractLayer == null) throw new InuringContractLayerNotFoundException(request.getInuringContractLayerId());


        InuringContractLayerPerilLimit inuringContractLayerPerilLimit = new InuringContractLayerPerilLimit(
                request.getInuringContractLayerId(),
                request.getPeril(),
                request.getLimit());
        inuringContractLayerPerilLimitRepository.save(inuringContractLayerPerilLimit);
    }

    public void deleteInuringContractLayerPerilLimitById(int inuringContractLayerPerilLimit){
        inuringContractLayerPerilLimitRepository.deleteByInuringContractLayerPerilLimitId(inuringContractLayerPerilLimit);
    }


//
//    public boolean checkPossibility(String type){
//        switch (type){
//            case "Reinstatement":
//                break;
//            case "PerilLimit":
//                break;
//            default: return false;
//        }
//    }




}
