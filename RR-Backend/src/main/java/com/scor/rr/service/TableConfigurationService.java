package com.scor.rr.service;

import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.UserRrEntity;
import com.scor.rr.domain.dto.TableConfigurationRequest;
import com.scor.rr.domain.entities.tableConfiguration.TableConfiguration;
import com.scor.rr.domain.entities.tableConfiguration.UserTable;
import com.scor.rr.domain.entities.tableConfiguration.UserTablePreferencesView;
import com.scor.rr.repository.tableConfiguration.TableConfigurationRepository;
import com.scor.rr.repository.tableConfiguration.UserTablePreferencesViewRepo;
import com.scor.rr.repository.tableConfiguration.UserTableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
public class TableConfigurationService {

    @Autowired
    UserTablePreferencesViewRepo userTablePreferencesViewRepo;

    @Autowired
    TableConfigurationRepository tableConfigurationRepository;

    @Autowired
    UserTableRepo userTableRepo;


    public UserTablePreferencesView saveConfig(TableConfigurationRequest request) {
        UserRrEntity user = ( (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        UserTablePreferencesView userTablePreferencesView;

        if(!tableConfigurationRepository.existsByTableContextEqualsAndTableNameEquals(request.getTableContext(), request.getTableName())) {
            TableConfigurationRequest config = new TableConfigurationRequest();

            config.setConfig(request.getConfig());
            config.setTableContext(request.getTableContext());
            config.setTableName(request.getTableName());

            userTablePreferencesView = this.initConfig(config);

        } else userTablePreferencesView = this.userTablePreferencesViewRepo.findByUserIdAndTableContextAndTableName(user.getUserId(), request.getTableContext(), request.getTableName());

        if(userTablePreferencesView.getUserTableId() != null) {
            Optional<UserTable> userTable =this.userTableRepo.findById(userTablePreferencesView.getUserTableId());

            userTable.ifPresent( userTable1 -> {
                userTable1.setUserColumns(request.getConfig());
                userTableRepo.saveAndFlush(userTable1);
            });

        } else {
            UserTable userTable = new UserTable();

            userTable.setTableConfiguration(userTablePreferencesView.getTableConfigurationId());
            userTable.setUserId(user.getUserId());
            userTable.setUserColumns(request.getConfig());
            userTable.setVersionId(1);

            userTableRepo.save(userTable);
        }

        return userTablePreferencesViewRepo.findByUserIdAndTableContextAndTableName(
                user.getUserId(), request.getTableContext(), request.getTableName()
        );
    }

    public UserTablePreferencesView initConfig(TableConfigurationRequest request) {
        UserRrEntity user = ( (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        TableConfiguration tableConfiguration = new TableConfiguration();

        tableConfiguration.setDefaultColumns(request.getConfig());
        tableConfiguration.setTableContext(request.getTableContext());
        tableConfiguration.setTableName(request.getTableName());

        tableConfigurationRepository.saveAndFlush(tableConfiguration);

        return userTablePreferencesViewRepo.findByUserIdAndTableContextAndTableName(
                null, request.getTableContext(), request.getTableName()
        );
    }

    public boolean checkConfig(String tableName, String tableContext) {
        return tableConfigurationRepository.existsByTableContextEqualsAndTableNameEquals(tableContext, tableName);
    }

    public UserTablePreferencesView getConfig(String tableName, String tableContext) {
        UserRrEntity user = ( (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return userTablePreferencesViewRepo.findByUserIdAndTableContextAndTableName(
                user.getUserId(), tableContext, tableName
        );
    }

}
