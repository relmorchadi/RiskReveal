package com.rr.riskreveal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import javax.persistence.Id;

@Projection(
        name = "customWorkspace",
        types = { ContractSearchResult.class }
)
public interface WorkspaceProjection {
//    @JsonProperty("id")
//    @Value("#{target.getId()}")
//    String getId();
//    @JsonProperty("workspaceId")
//    @Value("#{target.workSpaceId}")
    String getWorkSpaceId();
//    @JsonProperty("workspaceName")
//    @Value("#{target.workspaceName}")
    String getWorkspaceName();
//    @JsonProperty("year")
//    @Value("#{target.uwYear}")
    Integer getUwYear();
//    @JsonProperty("cedantCode")
//    @Value("#{target.cedantCode}")
    String getCedantCode();
//    @JsonProperty("cedantName")
//    @Value("#{target.cedantName}")
    String getCedantName();
//    @JsonProperty("countryName")
//    @Value("#{target.countryName}")
    String getCountryName();
}
