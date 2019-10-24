package com.scor.rr.response;

import com.scor.rr.entity.InuringInputAttachedPLT;
import com.scor.rr.entity.InuringInputNode;
import lombok.Data;

import java.util.List;

@Data
public class InuringInputNodeResponse {

    private InuringInputNode inuringInputNode;
    private List<InuringInputAttachedPLT> PLTHeaderIdList;

}
