package com.scor.rr.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RRFile {

    private String fileName;
    private String path;
    private String fqn;

    public RRFile(PetEntity pet){
        this.fileName= pet.getPeqtFileName();
        this.path= pet.getPeqtFilePath();
        this.fqn= pet.getPeqtFileFQN();
    }

}
