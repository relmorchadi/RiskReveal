package com.scor.rr.rest;

import com.scor.rr.domain.PltManagerView;
import com.scor.rr.domain.dto.PltFilter;
import com.scor.rr.service.PltBrowserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/plt")
public class PltBrowserResource {

    @Autowired
    PltBrowserService pltBrowserService;


    @GetMapping
    public List<PltManagerView> searchPltTable(PltFilter filter){
        return pltBrowserService.searchPltTable(filter);
    }

}
