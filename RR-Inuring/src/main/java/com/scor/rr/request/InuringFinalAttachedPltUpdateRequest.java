package com.scor.rr.request;

import com.scor.rr.dto.InuringFinalAttachePltDto;
import lombok.Data;

import java.util.List;

@Data
public class InuringFinalAttachedPltUpdateRequest {

    List<InuringFinalAttachePltDto> listOfPlts;

    public InuringFinalAttachedPltUpdateRequest(List<InuringFinalAttachePltDto> listOfPlts) {
        this.listOfPlts = listOfPlts;
    }

    public InuringFinalAttachedPltUpdateRequest() {
    }
}
