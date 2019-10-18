package com.scor.rr.rest;

import com.scor.rr.entity.InuringFinalNode;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.request.InuringFinalNodeUpdateRequest;
import com.scor.rr.service.InuringFinalNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/inuring/finaleNode")
public class InuringFinaleNodeController {

    @Autowired
    private InuringFinalNodeService inuringFinalNodeService;

    @PostMapping("update")
    public ResponseEntity<?> updateInuringFinalNode(@RequestBody InuringFinalNodeUpdateRequest request) throws RRException {
        inuringFinalNodeService.updateInuringFinalNode(request);
        return ResponseEntity.ok("it's working");
    }


}
