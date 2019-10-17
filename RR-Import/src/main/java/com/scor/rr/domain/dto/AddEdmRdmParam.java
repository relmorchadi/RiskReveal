package com.scor.rr.domain.dto;


import lombok.Data;

import java.util.List;

@Data
public class AddEdmRdmParam {

    List<EdmHeader> emds;
    List<RdmHeader> rmds;


    public static class EdmHeader {
        public Integer edmId;
        public String edmName;
    }


    public static class RdmHeader {
        public Integer rdmId;
        public String rdmName;
    }
}
