package com.scor.rr.domain.Response;

import com.google.gson.Gson;
import com.scor.rr.domain.entities.DashboardView;
import lombok.Data;
import springfox.documentation.spring.web.json.Json;

import java.util.List;
import java.util.Map;

@Data
public class DashboardDataResponse {

    private int refCount;
    private List<Map<String,Object>> content;
}
