package com.scor.rr.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

@Component
public class FileBaseImportService {

    @Value("${filebaseimport.sandbox.unc}")
    private String fileUnc = "UNKNOWN";

    public Set<String> listFilesFromSandbox(String path) {
        return Stream.of(new File(this.fileUnc.concat(ofNullable(path).orElse(""))).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(toSet());
    }

    public Set<String> listFoldersFromSandbox(String path) {
        return Stream.of(new File(this.fileUnc.concat(ofNullable(path).orElse(""))).listFiles())
                .filter(file -> file.isDirectory())
                .map(File::getName)
                .collect(toSet());
    }

    public String readFile(String fileName) throws IOException {
        return this.readFromInputStream(
                new FileInputStream(new File(this.fileUnc.concat("\\").concat(fileName)))
        );

    }


    private String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

}
