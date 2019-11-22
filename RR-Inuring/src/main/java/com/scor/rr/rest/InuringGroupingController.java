package com.scor.rr.rest;

import com.scor.rr.exceptions.RRException;
import com.scor.rr.request.InuringGroupingRequest;
import com.scor.rr.service.InuringGroupingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/inuring/grouping")
public class InuringGroupingController {

    @Autowired
    private InuringGroupingService inuringGroupingService;


    @PostMapping("create")
    public ResponseEntity<?> groupPLTs(@RequestBody InuringGroupingRequest request) throws RRException, NoSuchFieldException, IllegalAccessException {
        inuringGroupingService.groupPlts(request);
        return ResponseEntity.ok("it's working");
    }
}
