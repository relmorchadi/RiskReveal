package com.scor.rr.rest;

import com.scor.rr.JsonFormat.InuringPackageJsonResponse;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.request.InuringPackageCreationRequest;
import com.scor.rr.request.InvalidateNodeRequest;
import com.scor.rr.response.InuringPackageDetailsResponse;
import com.scor.rr.service.InuringPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/inuring/inuringPackage")
public class InuringPackageController {

    @Autowired
    private InuringPackageService inuringPackageService;

    @PostMapping("create")
    public ResponseEntity<?> createInuringPackage(@RequestBody InuringPackageCreationRequest request) throws RRException {
        inuringPackageService.createInuringPackage(request);
        return ResponseEntity.ok("it's working");
    }

    @PostMapping("read")
    public InuringPackageDetailsResponse readInuringPackageDetails(@RequestParam("id") long id ) throws RRException {
        return inuringPackageService.readInuringPackageDetail(id);
    }

    @PostMapping("getJSON")
    public InuringPackageJsonResponse getJSON(@RequestParam("id") long id ) throws RRException {
        return inuringPackageService.getJSON(id);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteInuringPackage(@RequestParam("id") long id) throws RRException{
        inuringPackageService.deleteInuringPackage(id);
        return ResponseEntity.ok("it has been successfully deleted");
    }

    @PostMapping("invalidate")
    public ResponseEntity<?> invalidateNode(@RequestBody InvalidateNodeRequest invalidateNodeRequest) throws RRException{
        inuringPackageService.invalidateNode(invalidateNodeRequest.getInuringNodeType(),invalidateNodeRequest.getNodeId());
        return ResponseEntity.ok("it was invalidated correctly");
    }



}
