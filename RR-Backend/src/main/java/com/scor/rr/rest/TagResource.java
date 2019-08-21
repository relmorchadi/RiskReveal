package com.scor.rr.rest;

import com.scor.rr.domain.dto.UserResponse;
import com.scor.rr.service.TagService;
import com.scor.rr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/tags")
public class TagResource {

    @Autowired
    TagService tagService;



}
