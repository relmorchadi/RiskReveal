package com.scor.rr.service;


import com.scor.rr.entity.*;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.InuringPackageNotFoundException;
import com.scor.rr.repository.*;
import com.scor.rr.request.InuringContractNodeBulkDeleteRequest;
import com.scor.rr.request.InuringContractNodeCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InuringContractNodeService {

    @Autowired
    private InuringPackageRepository inuringPackageRepository;

    @Autowired
    private InuringContractNodeRepository inuringContractNodeRepository;

    @Autowired
    private InuringContractLayerRepository inuringContractLayerRepository;

    @Autowired
    private InuringContractLayerParamRepository inuringContractLayerParamRepository;

    @Autowired
    private RefFMFContractTypeRepository refFMFContractTypeRepository;

    @Autowired
    private RefFMFContractAttributeRepository refFMFContractAttributeRepository;

    @Autowired
    private RefFMFContractTypeAttributeMapRepository refFMFContractTypeAttributeMapRepository;

    public void createInuringContractNode(InuringContractNodeCreationRequest request) throws RRException {

        InuringPackage inuringPackage = inuringPackageRepository.findByInuringPackageId(request.getInuringPackageId());
        if (inuringPackage == null) throw new InuringPackageNotFoundException(request.getInuringPackageId());

        InuringContractNode inuringContractNode = inuringContractNodeRepository.saveAndFlush(new InuringContractNode(request.getInuringPackageId(),request.getInuringContractNodeType()));


        InuringContractLayer inuringContractLayer = inuringContractLayerRepository.saveAndFlush(new InuringContractLayer(1,
                inuringContractNode.getInuringContractNodeId(),
                1,"",""));

        List<RefFMFContractAttribute> listOfAttributes = refFMFContractTypeAttributeMapRepository.getAttributesForContract(inuringContractNode.getContractTypeCode());

        if(listOfAttributes != null){

        for(RefFMFContractAttribute attribute: listOfAttributes) {
            InuringContractLayerParam inuringContractLayerParam = new InuringContractLayerParam(inuringContractLayer.getInuringContractLayerId(), attribute.getUIAttributeName(),
                    attribute.getDataType(), attribute.getDefaultValue());
            System.out.println("this is getting in the loop" + inuringContractLayer);

            inuringContractLayerParamRepository.save(inuringContractLayerParam);
        }
        }
    }

    public void deleteInuringContractNodes(InuringContractNodeBulkDeleteRequest request){
        for(InuringContractNode inuringContractNode: request.getInuringContractNodes()){
            inuringContractNodeRepository.deleteById(inuringContractNode.getInuringContractNodeId());
        }
    /**Still have to test if the Cascade delete works**/
    }


}
