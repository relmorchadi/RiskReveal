package com.scor.rr.rest;

import com.scor.rr.service.abstraction.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/contract")
public class ContractResource {

    @Autowired
    private ContractService contractService;

    @GetMapping(value = "/get-car-info")
    public ResponseEntity<?> getCarInformation(@RequestParam Long projectId) {
        try {
            return new ResponseEntity<>(contractService.getContractInfo(projectId), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Operation has failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
