package com.scor.rr.service;


import com.scor.rr.entity.*;
import com.scor.rr.enums.InuringElementType;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.InuringContractLayerNotFoundException;
import com.scor.rr.exceptions.inuring.InuringContractNodeNotFoundException;
import com.scor.rr.exceptions.inuring.InuringContractTypeNotFoundException;
import com.scor.rr.exceptions.inuring.InuringPackageNotFoundException;
import com.scor.rr.repository.*;
import com.scor.rr.request.InuringContractNodeBulkDeleteRequest;
import com.scor.rr.request.InuringContractNodeCreationRequest;
import com.scor.rr.response.InuringContractLayerDetailsResponse;
import com.scor.rr.response.InuringContractNodeDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
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
    private InuringFilterCriteriaRepository inuringFilterCriteriaRepository;

    @Autowired
    private InuringContractLayerPerilLimitRepository inuringContractLayerPerilLimitRepository;

    @Autowired
    private InuringContractLayerReinstatementDetailRepository inuringContractLayerReinstatementDetailRepository;

    @Autowired
    private RefFMFContractTypeRepository refFMFContractTypeRepository;

    @Autowired
    private RefFMFContractAttributeRepository refFMFContractAttributeRepository;

    @Autowired
    private RefFMFContractTypeAttributeMapRepository refFMFContractTypeAttributeMapRepository;

    /**
     * i made some changes on the dataModel to make this one work with the REFFMF table in the current db
     **/
    public void createInuringContractNode(InuringContractNodeCreationRequest request) throws RRException {

        List<RefFMFContractAttribute> listOfAttributes = refFMFContractAttributeRepository.getAttributesForContract(request.getContractTypeCode());
        if(listOfAttributes == null || listOfAttributes.isEmpty()) throw new InuringContractTypeNotFoundException(request.getContractTypeCode());

        InuringPackage inuringPackage = inuringPackageRepository.findByInuringPackageId(request.getInuringPackageId());
        if (inuringPackage == null) throw new InuringPackageNotFoundException(request.getInuringPackageId());

        InuringContractNode inuringContractNode = inuringContractNodeRepository.saveAndFlush(new InuringContractNode(request.getInuringPackageId(), request.getContractTypeCode()));


        InuringContractLayer inuringContractLayer = inuringContractLayerRepository.saveAndFlush(new InuringContractLayer(1,
                inuringContractNode.getInuringContractNodeId(),
                1, "", ""));


            for (RefFMFContractAttribute attribute : listOfAttributes) {
                if(attribute.getUISectionName().equals("layer List") || attribute.getUISectionName().equals("Layer List")){
                InuringContractLayerParam inuringContractLayerParam = new InuringContractLayerParam(inuringContractLayer.getInuringContractLayerId(), attribute.getUIAttributeName(),
                        attribute.getDataType(), attribute.getDefaultValue());

                inuringContractLayerParamRepository.save(inuringContractLayerParam);
            }
                if(attribute.getUIAttributeName().equals("Contract Name")){
                    inuringContractNode.setContractName(attribute.getDefaultValue());
                }
                if(attribute.getUIAttributeName().equals("Occurrence Basis")){
                    inuringContractNode.setOccurenceBasis(attribute.getDefaultValue());
                }
                if(attribute.getUIAttributeName().equals("Claims Basis")){
                    inuringContractNode.setClaimsBasis(attribute.getDefaultValue());
                }
                if(attribute.getUIAttributeName().equals("Contract Currency Code")){
                    inuringContractNode.setContractCurrency(attribute.getDefaultValue());
                }
                if(attribute.getUIAttributeName().equals("Contract Name")){
                    inuringContractNode.setContractName(attribute.getDefaultValue());
                }
            }

    }

    public void deleteInuringContractNode(long inuringContractNodeId) {
        inuringContractNodeRepository.deleteById(inuringContractNodeId);
    }

    public void deleteInuringContractNodes(InuringContractNodeBulkDeleteRequest request) {
        for (InuringContractNode inuringContractNode : request.getInuringContractNodes()) {
            inuringContractNodeRepository.deleteById(inuringContractNode.getInuringContractNodeId());
        }
        /**Still have to test if the Cascade delete works**/
    }

    public InuringContractNodeDetailsResponse readInuringContractNode(long inuringContractNodeId) throws RRException {
        InuringContractNode inuringContractNode = inuringContractNodeRepository.findByInuringContractNodeId(inuringContractNodeId);
        if (inuringContractNode == null) throw new InuringContractNodeNotFoundException(inuringContractNodeId);

        List<InuringContractLayer> inuringContractLayers = inuringContractLayerRepository.findByInuringContractNodeId(inuringContractNodeId);
        if (inuringContractLayers == null) throw new InuringContractLayerNotFoundException(inuringContractNodeId);

        List<InuringContractLayerDetailsResponse> inuringContractLayerDetailsResponsesList = new ArrayList<InuringContractLayerDetailsResponse>() {
        };

        for (InuringContractLayer inuringContractLayer : inuringContractLayers
        ) {
            InuringContractLayerDetailsResponse inuringContractLayerDetailsResponse = new InuringContractLayerDetailsResponse();
            inuringContractLayerDetailsResponse.setInuringContractLayer(inuringContractLayer);

            List<InuringContractLayerParam> inuringContractLayerParamList = inuringContractLayerParamRepository.findByInuringContractLayerId(inuringContractLayer.getInuringContractLayerId());
            if (inuringContractLayerParamList != null)
                inuringContractLayerDetailsResponse.setInuringContractLayerParamsList(inuringContractLayerParamList);

            List<InuringContractLayerPerilLimit> inuringContractLayerPerilLimitList = inuringContractLayerPerilLimitRepository.findByInuringContractLayerId(inuringContractLayer.getInuringContractLayerId());
            if (inuringContractLayerPerilLimitList != null)
                inuringContractLayerDetailsResponse.setInuringContractLayerPerilLimitList(inuringContractLayerPerilLimitList);

            List<InuringContractLayerReinstatementDetail> inuringContractLayerReinstatementDetailList = inuringContractLayerReinstatementDetailRepository.findByInuringContractLayerId(inuringContractLayer.getInuringContractLayerId());
            if (inuringContractLayerReinstatementDetailList != null)
                inuringContractLayerDetailsResponse.setInuringContractLayerReinstatementDetailList(inuringContractLayerReinstatementDetailList);

            List<InuringFilterCriteria> inuringFilterCriteriaList = inuringFilterCriteriaRepository.findByInuringObjectIdAndInuringObjectType(inuringContractLayer.getInuringContractLayerId(), InuringElementType.ContractLayer);
            if (inuringFilterCriteriaList != null)
                inuringContractLayerDetailsResponse.setInuringFilterCriteriaList(inuringFilterCriteriaList);

            inuringContractLayerDetailsResponsesList.add(inuringContractLayerDetailsResponse);


        }

        InuringContractNodeDetailsResponse inuringContractNodeDetailsResponse = new InuringContractNodeDetailsResponse();
        inuringContractNodeDetailsResponse.setInuringContractNode(inuringContractNode);
        inuringContractNodeDetailsResponse.setInuringContractLayerDetailsResponseList(inuringContractLayerDetailsResponsesList);

        return inuringContractNodeDetailsResponse;

    }

    /** the Update service needs to be discussed with the frontend dev because the popup has a lot of components**/

}
