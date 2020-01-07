package com.scor.rr.rest;

import com.scor.rr.exceptions.RRException;
import com.scor.rr.request.InuringFilterCriteriaCreationRequest;
import com.scor.rr.service.InuringFilterCriteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/inuring/filterCriteria")
public class InuringFilterCriteriaController {

    @Autowired
    private InuringFilterCriteriaService inuringFilterCriteriaService;


    @PostMapping("create")
    public ResponseEntity<?> createFilterCriteria(@RequestBody InuringFilterCriteriaCreationRequest request) throws RRException {
      inuringFilterCriteriaService.createInuringFilterCriteria(request);
      return ResponseEntity.ok("It's working");
    }
}
