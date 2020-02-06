package com.scor.rr.rest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/global")
public class GlobalResource {
    @Autowired
    Environment env;

    @GetMapping("/version")
    public String getVersion() {
        return StringUtils.remove(env.getProperty("rr.version"), "-SNAPSHOT");
    }

    @GetMapping("/env")
    public String getEnvironment(){
        return env.getProperty("rr.env");
    }

    @GetMapping("/prp")
    public String getPrp(@RequestParam String prp){
        return env.getProperty(prp);
    }

}
