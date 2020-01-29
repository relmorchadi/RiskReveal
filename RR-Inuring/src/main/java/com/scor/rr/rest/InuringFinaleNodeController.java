package com.scor.rr.rest;

import com.scor.rr.entity.InuringFinalAttachedPLT;
import com.scor.rr.entity.InuringFinalNode;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.request.InuringFinalAttachedPltUpdateRequest;
import com.scor.rr.request.InuringFinalNodeUpdateRequest;
import com.scor.rr.service.InuringFinalNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/inuring/finaleNode")
public class InuringFinaleNodeController {

    @Autowired
    private InuringFinalNodeService inuringFinalNodeService;

    @PutMapping("update")
    public ResponseEntity<?> updateInuringFinalNode(@RequestBody InuringFinalNodeUpdateRequest request) throws RRException {
        inuringFinalNodeService.updateInuringFinalNode(request);
        return ResponseEntity.ok("it's working");
    }

    @PostMapping("updateStructure")
    public ResponseEntity<?> updateStructure(@RequestParam int inuringPackageId) throws RRException, IllegalAccessException, NoSuchFieldException {
         inuringFinalNodeService.generateExpectedOutcomePLTS(inuringPackageId);
         return ResponseEntity.ok("it's working");
    }

    @GetMapping("getExpectedPLTs")
    public List<InuringFinalAttachedPLT> getExpectedPLTs(@RequestParam int inuringFinalNodeID){
        return inuringFinalNodeService.getListOfExpectedPLTs(inuringFinalNodeID);
    }

    @PutMapping("updatePLTCurrencyAndName")
    public ResponseEntity<?> updatePltCcy(@RequestBody InuringFinalAttachedPltUpdateRequest request) throws RRException {
        inuringFinalNodeService.updateFinalAttachedPLTs(request);
        return ResponseEntity.ok("Updated Successfully");
    }


}
