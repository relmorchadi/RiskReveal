package com.scor.rr.domain.model;

import lombok.Data;

import java.io.File;

@Data
public class PathNode {
    private File file;
    private String customName;

    public PathNode(File file, String customName) {
        this.file = file;
        this.customName = customName;
    }
}
