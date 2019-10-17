package com.scor.rr.rest;

import com.scor.rr.domain.UserTag;
import com.scor.rr.domain.dto.*;
import com.scor.rr.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tags")
public class TagResource {

    @Autowired
    TagService tagService;

    @PostMapping("/getSelection")
    TagManagerResponse getTagsByPltSelection(@RequestBody TagManagerRequest request) {
        return this.tagService.getTagsByPltSelection(request);
    }


    @PostMapping
    UserTag createUserTag(@RequestBody UserTagRequest request) {
        return this.tagService.createUserTag(request);
    }


    @PostMapping("/assign")
    List<UserTag> assignTagPlt(@RequestBody AssignTagPltRequest request) {
        return this.tagService.assignTagPlt(request);
    }


}
