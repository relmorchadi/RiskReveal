package com.scor.rr.rest;

import com.scor.rr.domain.PltManagerView;
import com.scor.rr.domain.dto.PltFilter;
import com.scor.rr.service.PltBrowserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/plt")
public class PltBrowserResource {

    @Autowired
    PltBrowserService pltBrowserService;


    @GetMapping
    public Page<PltManagerView> searchPltTable(PltFilter filter, @PageableDefault Pageable pageable){
        return pltBrowserService.searchPltTable(filter, pageable);
    }

}
