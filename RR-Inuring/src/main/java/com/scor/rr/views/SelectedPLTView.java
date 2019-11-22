package com.scor.rr.views;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "selectedPlt", schema = "dbo", catalog = "RiskReveal")
public class SelectedPLTView {
    @Id
    @Column(name = "pltId", nullable = false)
    private int pltId;

    @Column(name = "pltName")
    private String pltName;

    @Column(name = "grain")
    private String grain;

    @Column(name = "filePath")
    private String filePath;

    @Column(name = "sourceFileName")
    private String fileName;

    @Column(name = "currency")
    private String currency;

    @Column(name = "targetRAPId")
    private int targetRapId;

    @Column(name = "targetRAPCode")
    private String targetRapCode;

    @Column(name = "regionPeril")
    private String regionPeril;

    @Column(name = "peril")
    private String peril;

    @Column(name = "projectId")
    private int projectId;

}
