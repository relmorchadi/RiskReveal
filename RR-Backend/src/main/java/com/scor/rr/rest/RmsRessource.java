package com.scor.rr.rest;


import com.scor.rr.service.RmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/rms")
public class RmsRessource {

    @Autowired
    RmsService rmsService;


    @GetMapping("test")
    ResponseEntity<?> test(){
        rmsService.test();
        return ResponseEntity.ok().build();
    }

}
