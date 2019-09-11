package com.scor.rr.configuration.file;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class CopyFile {

    public static void copyFileFromPath(File file,String path) throws IOException {
        FileUtils.copyFileToDirectory(file,new File(path));

    }
}
