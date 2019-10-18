package com.scor.rr.rest;

import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.dto.TargetBuild.AssignTagToPltsRequest;
import com.scor.rr.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tags")
public class TagResource {

    @Autowired
    TagService tagService;

    @PostMapping("/getSelection")
    TagManagerResponse getTagsByPltSelection(@RequestBody TagManagerRequest request) {
        //return this.tagService.getTagsByPltSelection(request);
        return null;
    }

    @PostMapping("/assign")
    Boolean assignTagPlt(@RequestBody AssignTagToPltsRequest request) {
        return this.tagService.assignTagToPlts(request);
    }


}
