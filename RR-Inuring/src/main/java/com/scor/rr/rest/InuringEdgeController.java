package com.scor.rr.rest;

import com.scor.rr.entity.InuringEdge;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.request.InuringEdgeCreationRequest;
import com.scor.rr.request.InuringEdgeUpdateRequest;
import com.scor.rr.service.InuringEdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/inuring/edge")
public class InuringEdgeController {

    @Autowired
    private InuringEdgeService inuringEdgeService;

    @PostMapping("create")
    public ResponseEntity<?> createIuringEdge(@RequestBody InuringEdgeCreationRequest request) throws RRException {
        inuringEdgeService.createInuringEdge(request);
        return ResponseEntity.ok("it's working");
    }

    @PutMapping("update")
    public ResponseEntity<?> updateInuringEdge(@RequestBody InuringEdgeUpdateRequest request) throws RRException{
        inuringEdgeService.updateInuringEdge(request);
        return ResponseEntity.ok("it has been updated successfully");
    }

    @PostMapping("read")
    public InuringEdge readInuringEdge(@RequestParam("id") int id) throws RRException{
        return inuringEdgeService.readInuringEdge(id);
    }


    @DeleteMapping("delete")
    public ResponseEntity<?> deleteInuringEdge(@RequestParam("id") int id){
        inuringEdgeService.deleteInuringEdgeById(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
