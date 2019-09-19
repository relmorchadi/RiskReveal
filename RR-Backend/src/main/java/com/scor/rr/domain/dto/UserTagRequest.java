package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


public class UserTagRequest {
    public String tagName;
    public String tagColor;
    public Integer userId;
    public String wsId;
    public Integer uwYear;
    public List<String> selectedPlts;
}
