package com.scor.rr.domain.entities.PLTManager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "vw_PltManagerGrid")
public class PLTManagerAll extends PLTManagerView {

    PLTManagerAll(PLTManagerView view) {
        super(view);
    }
}
