package com.scor.rr.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by U004602 on 17/10/2019.
 */
@RestController
@RequestMapping("api/inuring")
public class InuringRest {
    @GetMapping("beatheart")
    public String getBeatHeart() {
        Date date = new Date();
        Long mem = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        return "At " + date + " : using " + mem + " MB";
    }
}
