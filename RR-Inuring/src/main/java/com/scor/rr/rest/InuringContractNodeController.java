package com.scor.rr.rest;


import com.scor.rr.exceptions.RRException;
import com.scor.rr.request.InuringContractNodeCreationRequest;
import com.scor.rr.response.InuringContractNodeDetailsResponse;
import com.scor.rr.service.InuringContractNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/inuring/contractNode")
public class InuringContractNodeController {

    @Autowired
    private InuringContractNodeService inuringContractNodeService;

    @PostMapping("create")
    public ResponseEntity<?> createInuringContract(@RequestBody InuringContractNodeCreationRequest request) throws RRException {
        inuringContractNodeService.createInuringContractNode(request);
        return ResponseEntity.ok("it's working");
    }

    @PostMapping("read")
    public InuringContractNodeDetailsResponse readInuringContract(@RequestParam("id") long id) throws RRException{
        return inuringContractNodeService.readInuringContractNode(id);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteInuringContract(@RequestParam("id") long id){
        inuringContractNodeService.deleteInuringContractNode(id);
        return ResponseEntity.ok("it's working");
    }
}
