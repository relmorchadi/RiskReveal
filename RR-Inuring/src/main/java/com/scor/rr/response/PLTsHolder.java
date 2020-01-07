package com.scor.rr.response;

import com.scor.rr.domain.PltHeaderEntity;
import lombok.Data;

import java.util.List;

@Data
public class PLTsHolder {
    private List<PltHeaderEntity>  listOfPLTS;

    public PLTsHolder() {
    }

    public PLTsHolder( List<PltHeaderEntity> listOfPLTS) {
        this.listOfPLTS = listOfPLTS;
    }
}
