package com.scor.rr.rest;

import com.scor.rr.domain.entities.userPreferences.TablePreferences;
import com.scor.rr.domain.entities.userPreferences.UserPreference;
import com.scor.rr.domain.entities.userPreferences.UserPreferenceView;
import com.scor.rr.service.TablePreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("api/table-preferences")
public class TablePreferencesResource {

    @Autowired
    private TablePreferencesService tablePreferencesService;

    @GetMapping("/{uIPage}/{tableName}")
    public ResponseEntity<TablePreferences> getTablePreferences(
            @PathVariable("uIPage") String uIPage,
            @PathVariable("tableName") String tableName){
        return ResponseEntity.of(ofNullable(tablePreferencesService.getTablePreferencesByUser(uIPage, tableName)));
    }

    @PostMapping
    public ResponseEntity<TablePreferences> addTablePreferences(@RequestBody TablePreferences tablePreferences){
        return ResponseEntity.of( ofNullable(tablePreferencesService.saveOrUpdateTablePreferences(tablePreferences)) );
    }


}
