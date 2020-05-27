package com.scor.rr.rest;

import com.scor.rr.domain.Response.GridDataResponse;
import com.scor.rr.domain.dto.ColumnFilter;
import com.scor.rr.domain.dto.TableConfigurationRequest;
import com.scor.rr.domain.entities.tableConfiguration.UserTablePreferencesView;
import com.scor.rr.domain.requests.GridDataRequest;
import com.scor.rr.service.PLTManagerService;
import com.scor.rr.service.TableConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/plt-manager")
public class PLTManagerResource {

    @Autowired
    PLTManagerService pltManagerService;

    @Autowired
    TableConfigurationService tableConfigurationService;

    @PostMapping("/grouped")
    public GridDataResponse getGroupedPLTs(@RequestBody GridDataRequest<List<ColumnFilter>> request) { return this.pltManagerService.getGroupedPLTs(request);}

    @PostMapping("/config")
    public UserTablePreferencesView saveConfig(@RequestBody TableConfigurationRequest request) { return this.tableConfigurationService.saveConfig(request);}

    @GetMapping("/config")
    public UserTablePreferencesView getConfig(@RequestParam String tableName, @RequestParam String tableContext) { return this.tableConfigurationService.getConfig(tableName, tableContext);}

    @PostMapping("/initConfig")
    public UserTablePreferencesView initConfig(@RequestBody TableConfigurationRequest request) { return this.tableConfigurationService.initConfig(request);}

    @GetMapping("/config/check")
    public Boolean checkConfig(@RequestParam String tableName, @RequestParam String tableContext) { return this.tableConfigurationService.checkConfig(tableName, tableContext);}
}
