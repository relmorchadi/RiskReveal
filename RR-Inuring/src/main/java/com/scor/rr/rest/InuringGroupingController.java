package com.scor.rr.rest;

import com.scor.rr.exceptions.RRException;
import com.scor.rr.request.InuringGroupingRequest;
import com.scor.rr.request.InuringSplittingRequest;
import com.scor.rr.service.InuringGroupingService;
import com.scor.rr.service.InuringSplittingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/inuring/grouping")
public class InuringGroupingController {

    @Autowired
    private InuringGroupingService inuringGroupingService;
    @Autowired
    private InuringSplittingService inuringSplittingService;


    @PostMapping("create")
    public ResponseEntity<?> groupPLTs(@RequestBody InuringGroupingRequest request) throws RRException, NoSuchFieldException, IllegalAccessException, IOException {
        inuringGroupingService.groupPlts(request);
        return ResponseEntity.ok("it's working");
    }

    @PostMapping("split")
    public ResponseEntity<?> splitPLTs(@RequestBody InuringSplittingRequest request) throws RRException, NoSuchFieldException, IllegalAccessException, IOException, CloneNotSupportedException {
        inuringSplittingService.SplitPLTintoIndividual(request);
        return ResponseEntity.ok("it's working");
    }
}
