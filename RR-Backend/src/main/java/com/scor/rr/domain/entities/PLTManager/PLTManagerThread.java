package com.scor.rr.domain.entities.PLTManager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "vw_PLTManagerThread_tst")
public class PLTManagerThread extends PLTManagerView {

    public List<String> getPath() {
        List<String> path = new ArrayList<>();
        path.add(this.getPltId().toString());
        path.add(this.pureId.toString());
        return path;
    }

    @Column(name = "pureId")
    private Long pureId;

}
