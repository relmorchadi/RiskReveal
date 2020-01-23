package com.scor.rr.rest;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.IOException;

@RestController
@RequestMapping("api/global")
public class GlobalResource {
    @Autowired
    Environment env;

    @GetMapping("/version")
    public String getVersion() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        return (String) model.getProperties().get("rr_version");
    }

    @GetMapping("/env")
    public String getEnvironment(){
        return env.getProperty("rr.env");
    }
}
