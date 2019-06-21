package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignUpdatePltsRequest {

    public List<Integer> selectedTags;
    public List<Integer> unselectedTags;
    public List<String> plts;
    public String wsId;
    public Integer uwYear;
}
