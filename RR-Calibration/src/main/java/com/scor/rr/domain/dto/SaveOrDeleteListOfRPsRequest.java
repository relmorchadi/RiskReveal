package com.scor.rr.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class SaveOrDeleteListOfRPsRequest {
    List<Integer> rps;
    String screen;
}
