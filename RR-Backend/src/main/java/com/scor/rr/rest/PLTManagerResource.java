package com.scor.rr.rest;

import com.scor.rr.domain.Response.GridDataResponse;
import com.scor.rr.domain.dto.ColumnFilter;
import com.scor.rr.domain.dto.PLTManagerFilter;
import com.scor.rr.domain.requests.GridDataRequest;
import com.scor.rr.service.PLTManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/plt-manager")
public class PLTManagerResource {

    @Autowired
    PLTManagerService pltManagerService;

    @PostMapping("/grouped")
    public GridDataResponse getGroupedPLTs(@RequestBody GridDataRequest<List<ColumnFilter>> request) { return this.pltManagerService.getGroupedPLTs(request);}

}
