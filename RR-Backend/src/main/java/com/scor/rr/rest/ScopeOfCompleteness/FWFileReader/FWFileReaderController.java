package com.scor.rr.rest.ScopeOfCompleteness.FWFileReader;


import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.ScopeAndCompleteness.ProjectForewriterExpectedScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/ScopeAndCompleteness/FWFileReader")
public class FWFileReaderController {


    @Autowired
    private ProjectForewriterExpectedScopeService projectForewriterExpectedScopeService;

    @PostMapping("create")
    public ResponseEntity<?> storeFWDataForExpectedScope(@RequestParam("fileName") String fileName ) throws RRException {
        projectForewriterExpectedScopeService.storeTheExpectedScopeFac(fileName);
        return ResponseEntity.ok("Saved successfully");
    }
}
