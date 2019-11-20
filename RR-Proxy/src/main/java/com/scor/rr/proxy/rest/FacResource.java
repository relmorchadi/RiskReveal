package com.scor.rr.proxy.rest;


import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/proxy")
public class FacResource {


    @GetMapping("test")
    String test() {
        return "OK";
    }



}
