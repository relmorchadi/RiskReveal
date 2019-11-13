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
@Table(name = "selectedpltView", schema = "dbo")
public class SelectedPLTView {

    @Id
    @Column(name = "sourceName")
    private int sourceName;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "pltName")
    private String path;

    @Column(name = "currency")
    private String currency;

    @Column(name = "targetCurrency")
    private String targetCurrency;

    @Column(name = "targetRapId")
    private int targetRapId;

    @Column(name = "targetRapCode")
    private String targetRapCode;

    @Column(name = "regionPeril")
    private String regionPeril;

    @Column(name = "peril")
    private String peril;

    @Column(name = "grain")
    private String grain;

    @Column(name = "pltStructureCode")
    private int pltStructureCode;

}
