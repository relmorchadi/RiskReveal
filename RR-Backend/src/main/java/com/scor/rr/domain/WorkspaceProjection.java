package com.scor.rr.domain;

import org.springframework.data.rest.core.config.Projection;

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
//    @JsonProperty("clientName")
//    @Value("#{target.clientName}")
    String getCedantName();
//    @JsonProperty("countryName")
//    @Value("#{target.countryName}")
    String getCountryName();
}
