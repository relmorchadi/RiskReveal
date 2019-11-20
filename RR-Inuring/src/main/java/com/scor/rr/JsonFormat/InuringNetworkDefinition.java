package com.scor.rr.JsonFormat;

import lombok.Data;

import java.util.List;

@Data
public class InuringNetworkDefinition {
    private List<InputNodeList> InputNodeList;
    private List<ContractNodeList> ContractNodeList;
    private List<EdgeList> EdgeList;
}
