package com.scor.rr.ws;

import com.scor.almf.ws.cat_analysis_service.CreateCatAnalysisRequest;
import com.scor.almf.ws.cat_analysis_service.CreateCatAnalysisResponse;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by u004602 on 26/12/2019.
 */
@WebService(targetNamespace = "http://scor.com/almf/ws/cat-analysis-service")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL,parameterStyle= SOAPBinding.ParameterStyle.BARE)
public interface CatAnalysisWebService {
    @WebMethod(action = "CreateCatAnalysis", operationName = "CreateCatAnalysis")
    CreateCatAnalysisResponse createCatAnalysis(@WebParam(name = "CreateCatAnalysisRequest") CreateCatAnalysisRequest request);
}
