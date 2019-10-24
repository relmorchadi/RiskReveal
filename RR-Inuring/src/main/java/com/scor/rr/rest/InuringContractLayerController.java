package com.scor.rr.rest;

import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.InuringContractLayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/inuring/contractLayer")
public class InuringContractLayerController {

    @Autowired
    private InuringContractLayerService inuringContractLayerService;

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteLayerById(@RequestParam("layerId") int layerId,@RequestParam("contractNodeId") int contractNodeId) throws RRException {
        inuringContractLayerService.deleteContractLayerById(layerId,contractNodeId);
        return ResponseEntity.ok("it has been deleted successfully");
    }
}
