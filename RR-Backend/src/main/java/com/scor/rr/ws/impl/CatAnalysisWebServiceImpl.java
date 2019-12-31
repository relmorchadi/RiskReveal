package com.scor.rr.ws.impl;

import com.scor.rr.service.CatRequestService;
import com.scor.rr.ws.CatAnalysisWebService;
import com.scor.almf.ws.cat_analysis_service.*;
import com.scor.almf.ws.commons.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by u004602 on 26/12/2019.
 */
@Service
@WebService(endpointInterface = "com.scor.rr.ws.CatAnalysisWebService",targetNamespace="http://scor.com/almf/ws/cat-analysis-service")
public class CatAnalysisWebServiceImpl implements CatAnalysisWebService {
    private static final Logger log= LoggerFactory.getLogger(CatAnalysisWebServiceImpl.class);

    @Autowired
    private CatRequestService catRequestService;

    @Override
    public CreateCatAnalysisResponse createCatAnalysis(CreateCatAnalysisRequest request) {
        List<CatRequestService.CatRequestDivision> divs = new LinkedList<>();
        final List<UWanalysisDivisionType> divisions = request.getContract().getUWanalysis().getDivision();
        for (UWanalysisDivisionType div : divisions) {
            divs.add(new CatRequestService.CatRequestDivision(
                    div.getCURRENCYCD(),
                    div.getDIVCOVERAGE(),
                    div.getDIVLOBCD(),
                    div.getDIVNUM().intValue(),
                    div.getPRINCIPALDIVISION().intValue()
            ));
        }

        CatRequestService.CatRequestData data = new CatRequestService.CatRequestData(
                request.getContract().getGeneral().getBUSINESSTYPE().intValue(),
                request.getContract().getGeneral().getENDORNUM().intValue(),
                request.getContract().getGeneral().getFACNUM(),
                request.getContract().getGeneral().getINSURNUM()==null?null:request.getContract().getGeneral().getINSURNUM().intValue(),
                request.getContract().getGeneral().getLABEL(),
                request.getContract().getGeneral().getLOBCD(),
                request.getContract().getGeneral().getORDERNUM().intValue(),
                request.getContract().getGeneral().getSECTORCD(),
                request.getContract().getGeneral().getSUBSIDIARYCD().intValue(),
                request.getContract().getGeneral().getUWYEAR().intValue(),
                request.getContract().getUWanalysis().getGeneral().getUWANALYSISID().intValue(),
                request.getContract().getUWanalysis().getGeneral().getUWANALYSISNAME(),
                divs,
                request.getUSER().getUSERCD(),
                request.getUSER().getUSERFIRSTNAME(),
                request.getUSER().getUSERLASTNAME()
        );

        CreateCatAnalysisResponse response = new CreateCatAnalysisResponse();
        response.setServiceCallStatus(new ServiceCallStatus());
        response.setCatAnalysisRequestID(new CatAnalysisRequestID());
        try
        {
            String catAnalysisId = catRequestService.createRequest(data);
            response.getCatAnalysisRequestID().setID(catAnalysisId);
            response.getServiceCallStatus().setStatus(Status.OK);
            response.getServiceCallStatus().setErrorCount(BigInteger.ZERO);
            response.getServiceCallStatus().getErrorMessage().add("None");
        }
        catch(Throwable th)
        {
            log.error("",th);
            response.getCatAnalysisRequestID().setID("CAR-ERROR");
            response.getServiceCallStatus().setStatus(Status.ERR);
            response.getServiceCallStatus().setErrorCount(BigInteger.ONE);
            response.getServiceCallStatus().getErrorMessage().add(th.getMessage());
        }
        return response;
    }
}
