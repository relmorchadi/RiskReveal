package com.scor.rr.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class SaveListOfRPsRequest {
    List<Integer> rps;
    Long userId;
}
