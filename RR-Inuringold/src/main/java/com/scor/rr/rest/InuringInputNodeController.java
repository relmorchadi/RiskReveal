package com.scor.rr.rest;

import com.scor.rr.exceptions.RRException;
import com.scor.rr.request.InuringInputNodeCreationRequest;
import com.scor.rr.request.InuringInputNodeUpdateRequest;
import com.scor.rr.response.InuringInputNodeResponse;
import com.scor.rr.service.InuringInputNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/inuring/inputNode")
public class InuringInputNodeController {

    @Autowired
    private InuringInputNodeService inuringInputNodeService;

    @PostMapping("create")
    public ResponseEntity<?> createInputNode(@RequestBody InuringInputNodeCreationRequest request) throws RRException {
        inuringInputNodeService.createInuringInputNode(request);
        return ResponseEntity.ok("it's working");
    }

    @PutMapping("update")
    public ResponseEntity<?> updateInputNode(@RequestBody InuringInputNodeUpdateRequest request) throws RRException{
        inuringInputNodeService.updateInuringInputNode(request);
        return ResponseEntity.ok("it's updated");
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteInputNode(@RequestParam("id") int id){
        inuringInputNodeService.deleteInuringInputNode(id);
        return ResponseEntity.ok("it has been successfully deleted");
    }

    @PostMapping("read")
    public InuringInputNodeResponse readInputNode(@RequestParam("id")int id) throws RRException{
        return inuringInputNodeService.readInuringInputNodeDetails(id);
    }

}
