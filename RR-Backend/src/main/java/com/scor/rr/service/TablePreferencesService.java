package com.scor.rr.service;

import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.UserRrEntity;
import com.scor.rr.domain.entities.userPreferences.TablePreferences;
import com.scor.rr.domain.entities.userPreferences.UserPreferenceView;
import com.scor.rr.repository.userPreferences.TablePreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TablePreferencesService {

    @Autowired
    private TablePreferencesRepository tablePreferencesRepository;

    public TablePreferences getTablePreferencesByUser(String uIPage, String tableName) {
//        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return tablePreferencesRepository.findByUIPageAndAndTableName(uIPage, tableName)
                .orElseThrow(() -> new RuntimeException("No available user configuration for table name: "+ tableName));

    }

    public TablePreferences saveOrUpdateTablePreferences(TablePreferences tablePreferences) {

        tablePreferencesRepository.findById(tablePreferences.getTablePreferencesId()).
                ifPresent( tablePreference1 -> tablePreferences.setTablePreferencesId(tablePreference1.getTablePreferencesId()));

        return tablePreferencesRepository.save(tablePreferences);
    }

}
