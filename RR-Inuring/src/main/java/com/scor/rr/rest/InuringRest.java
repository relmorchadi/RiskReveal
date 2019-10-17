package com.scor.rr.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/inuring")
public class InuringRest {
    private final Logger logger = LoggerFactory.getLogger(InuringRest.class);


    @GetMapping("check")
    public ResponseEntity<?> checkHeart() {

//        this.logger.debug("start check heart ...");
//        this.inuringContractNodeService.createInuringContractNode();

        return ResponseEntity.ok("start check heart ");
    }
}
