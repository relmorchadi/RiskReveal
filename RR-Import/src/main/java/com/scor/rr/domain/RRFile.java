package com.scor.rr.domain;


import com.scor.rr.domain.model.PET;
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

    public RRFile(PET pet){
        this.fileName= pet.getPeqtFileName();
        this.path= pet.getPeqtFilePath();
        this.fqn= pet.getPeqtFileFQN();
    }

}
