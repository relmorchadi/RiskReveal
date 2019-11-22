package com.scor.rr.rest;

import com.scor.rr.entity.InuringNote;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.request.InuringNoteCreationRequest;
import com.scor.rr.request.InuringNoteUpdateRequest;
import com.scor.rr.service.InuringNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InuringNoteController {

    @Autowired
    private InuringNoteService inuringNoteService;

    @PostMapping("create")
    public ResponseEntity<?> createInuringNote(@RequestBody InuringNoteCreationRequest request) throws RRException {
        inuringNoteService.createNote(request);
        return ResponseEntity.ok("it's working");
    }

    @PostMapping("read")
    public List<InuringNote> readInuringNote(@RequestParam("id") long id){
        return inuringNoteService.readInuringNotes(id);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteNote(@RequestParam("id") long id){
        inuringNoteService.deleteInuringNote(id);
        return ResponseEntity.ok("it's working");
    }

    @PostMapping("update")
    public ResponseEntity<?> updateInuringNote(@RequestBody InuringNoteUpdateRequest request) throws RRException {
        inuringNoteService.updateInuringNote(request);
        return ResponseEntity.ok("it's working");
    }


}
