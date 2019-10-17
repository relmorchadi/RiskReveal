package com.scor.rr.rest;

import com.scor.rr.exceptions.RRException;
import com.scor.rr.request.InuringPackageCreationRequest;
import com.scor.rr.service.InuringPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
