package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTagRequest {
    public String tagId;
    public String tagName;
    public String tagColor;
    public Integer userId;
    public Set pltHeaders;
}
