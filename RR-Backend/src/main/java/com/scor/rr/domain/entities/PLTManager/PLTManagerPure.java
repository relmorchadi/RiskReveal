package com.scor.rr.domain.entities.PLTManager;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "vw_PLTManagerPure_tst")
public class PLTManagerPure extends PLTManagerView {



    public List<String> getPath() {
        List<String> path = new ArrayList<>();
        path.add(this.getPltId().toString());
        return path;
    }
}
