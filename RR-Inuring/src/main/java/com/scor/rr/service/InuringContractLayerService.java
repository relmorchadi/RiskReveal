package com.scor.rr.service;

import com.scor.rr.entity.InuringContractLayer;
import com.scor.rr.entity.InuringContractLayerParam;
import com.scor.rr.entity.InuringContractNode;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.InuringContractLayerNotFoundException;
import com.scor.rr.exceptions.inuring.InuringContractNodeNotFoundException;
import com.scor.rr.exceptions.inuring.InuringSingleLayerContractNodeException;
import com.scor.rr.repository.InuringContractLayerParamRepository;
import com.scor.rr.repository.InuringContractLayerRepository;
import com.scor.rr.repository.InuringContractNodeRepository;
import com.scor.rr.repository.RefFMFContractTypeRepository;
import com.scor.rr.request.InuringContractLayerCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class InuringContractLayerService {

    @Autowired
    private InuringContractNodeRepository inuringContractNodeRepository;

    @Autowired
    private InuringContractLayerParamRepository inuringContractLayerParamRepository;

    @Autowired
    private InuringContractLayerRepository inuringContractLayerRepository;

    @Autowired
    private RefFMFContractTypeRepository refFMFContractTypeRepository;

    public void addLayersToContractNode(InuringContractLayerCreationRequest request) throws RRException {

        InuringContractNode contractNode = inuringContractNodeRepository.findByInuringContractNodeId(request.getInuringContractNodeId());
        if (contractNode == null) throw new InuringContractNodeNotFoundException(request.getInuringContractNodeId());

        boolean multipleLayer = refFMFContractTypeRepository.findByContractTypeId(request.getContractTypeCode()).getMultipleLayer();

        if(!multipleLayer) throw new InuringSingleLayerContractNodeException(request.getInuringContractNodeId());

        request.getListOfLayers().forEach(inuringContractLayerDto -> {

            Set<InuringContractLayerParam> listOfAttributesPerLayer = new HashSet<>();

            InuringContractLayer inuringContractLayer = inuringContractLayerRepository.saveAndFlush(new InuringContractLayer(inuringContractLayerDto.getLayerNumber(),
                    request.getInuringContractNodeId(),
                    inuringContractLayerDto.getLayerSequence(),
                    inuringContractLayerDto.getLayerCode(),
                    inuringContractLayerDto.getLayerDescription()));

            inuringContractLayerDto.getListOfAttributes().forEach(inuringContractLayerParamDto ->{
                InuringContractLayerParam inuringContractLayerParam = new InuringContractLayerParam(
                        inuringContractLayer.getInuringContractLayerId(),
                        inuringContractLayerParamDto.getParamName(),
                        inuringContractLayerParamDto.getParamType(),
                        inuringContractLayerParamDto.getParamValue());

                listOfAttributesPerLayer.add(inuringContractLayerParam);

            });
            inuringContractLayerParamRepository.saveAll(listOfAttributesPerLayer);
        });


    }
/** needs to check if it's the last layer, if it is then throw an exception**/
    public void deleteContractLayerById(int contractLayerId, int contractNodeId) throws  RRException{
         InuringContractLayer inuringContractLayer = inuringContractLayerRepository.findByInuringContractLayerId(contractLayerId);
         if(inuringContractLayer == null) throw new InuringContractLayerNotFoundException(contractLayerId);

        int layerNumber = inuringContractLayer.getLayerNumber();
        inuringContractLayerRepository.deleteByInuringContractLayerId( contractLayerId );

        reorderTheLayers(layerNumber, contractNodeId);
    }


    private void reorderTheLayers(int layerNumber, int contractNodeId){
        inuringContractLayerRepository.reorderTheLayers(layerNumber,contractNodeId);
    }


}
