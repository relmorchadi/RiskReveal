package com.scor.rr.domain.entities.cat;

import com.scor.rr.domain.entities.workspace.Project;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "TTGlobalView")
@Data
public class GlobalView {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String importStatus;

    private String name;

    private String notes;

    private Boolean defaultview;

    private Integer runId;

    @OneToOne(fetch = FetchType.LAZY)
    private Project project;
}
