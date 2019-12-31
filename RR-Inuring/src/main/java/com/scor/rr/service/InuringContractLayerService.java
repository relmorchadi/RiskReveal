package com.scor.rr.service;

import com.scor.rr.dto.InuringContractLayerParamDto;
import com.scor.rr.entity.InuringContractLayer;
import com.scor.rr.entity.InuringContractLayerParam;
import com.scor.rr.entity.InuringContractNode;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.*;
import com.scor.rr.repository.InuringContractLayerParamRepository;
import com.scor.rr.repository.InuringContractLayerRepository;
import com.scor.rr.repository.InuringContractNodeRepository;
import com.scor.rr.repository.RefFMFContractTypeRepository;
import com.scor.rr.request.InuringContractLayerCreationRequest;
import com.scor.rr.request.InuringContractLayerUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
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

    public void updateContractLayer(InuringContractLayerUpdateRequest request) throws RRException{
        InuringContractLayer layer = inuringContractLayerRepository.findByInuringContractLayerId(request.getLayerId());
        if(layer == null) throw new InuringContractLayerNotFoundException(request.getLayerId());

        if(request.getListOfAttributes() != null && !request.getListOfAttributes().isEmpty()){
            List<InuringContractLayerParam> listOfLayersParam = inuringContractLayerParamRepository.findByInuringContractLayerId(request.getLayerId());
            for(InuringContractLayerParamDto item :request.getListOfAttributes()){
                boolean check = false;
                for(InuringContractLayerParam param : listOfLayersParam){
                    if(item.getParamName().equals(param.getParamName())){
                        check = true;
                        param.setParamValue(item.getParamValue());
                    }
                }
                if(!check) throw new InuringContractLayerParamNotFoundException(item.getParamName());
                inuringContractLayerParamRepository.saveAll(listOfLayersParam);
            }
        }
    }

    public void deleteContractLayerById(long contractLayerId, long contractNodeId) throws  RRException{
         InuringContractLayer inuringContractLayer = inuringContractLayerRepository.findByInuringContractLayerId(contractLayerId);
         if(inuringContractLayer == null) throw new InuringContractLayerNotFoundException(contractLayerId);

        int layerNumber = inuringContractLayer.getLayerNumber();
        if(inuringContractLayerRepository.countInuringContractLayerByInuringContractNodeId(contractNodeId) == 1) throw new DeleteOfLastLayerException(contractLayerId);
        inuringContractLayerRepository.deleteByInuringContractLayerId( contractLayerId );

        reorderTheLayers(layerNumber, contractNodeId);
    }


    private void reorderTheLayers(int layerNumber, long contractNodeId){
        inuringContractLayerRepository.reorderTheLayers(layerNumber,contractNodeId);
    }


}
