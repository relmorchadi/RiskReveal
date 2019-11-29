//package com.scor.rr.rest;
//
//
//import com.scor.rr.domain.FacContract;
//import com.scor.rr.service.FacService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("api/fac")
//public class FacResource {
//
//
//    @Autowired
//    FacService facService;
//
//
//    @GetMapping()
//    Page<FacContract> getFacContracts(FacContract filter, @PageableDefault(size = 10) Pageable pageable) {
//        return facService.getFacContract(filter, pageable);
//    }
//
//
//    @PostMapping()
//    ResponseEntity saveFacContract(@RequestBody FacContract facContract) {
//        return ResponseEntity.ok(
//                facService.saveFacContract(facContract)
//        );
//    }
//
//    @GetMapping("datasources")
//    ResponseEntity<?> getDataSources(){
//        return ResponseEntity.ok(facService.getDatasources());
//    }
//
//    @GetMapping("analysis-basic")
//    ResponseEntity<?> findAnalysisBasic(@RequestParam("rdmId") Integer rdmId, @RequestParam("rdmName") String rdmName, @RequestParam("analysisName") String analysisName) {
//        return ResponseEntity.ok(facService.findAnalysisBasic(rdmId, rdmName, analysisName));
//    }
//
//    @GetMapping("analysis-detail")
//    ResponseEntity<?> findAnalysisDetail(@RequestParam("analysisId") Integer analysisId, @RequestParam("analysisName") String analysisName){
//        return ResponseEntity.ok(facService.findAnalysisDetail(analysisId, analysisName));
//    }
//
//    @GetMapping("portfolio")
//    ResponseEntity<?> findPortfolio(@RequestParam("edmId") Integer edmId, @RequestParam("edmName") String edmName, @RequestParam("portNum") String portNum) {
//        return ResponseEntity.ok(facService.findPortfolio(edmId, edmName, portNum));
//    }
//
//
//
//}
