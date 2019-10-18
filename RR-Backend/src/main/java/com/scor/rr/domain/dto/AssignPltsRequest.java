package com.scor.rr.domain.dto;

import com.scor.rr.domain.UserTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignPltsRequest {

    public UserTag tag;
    public List<String> plts;
    public String wsId;
    public Integer uwYear;
}
