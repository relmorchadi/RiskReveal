package com.scor.rr.JsonFormat;

import lombok.Data;

import java.util.List;

@Data
public class InputNodeList {

    private int index;
    private int id;
    private String sign;
    private int nbSelectedPLT;
    private List<SelectedPLT> selectedPLT;


}
