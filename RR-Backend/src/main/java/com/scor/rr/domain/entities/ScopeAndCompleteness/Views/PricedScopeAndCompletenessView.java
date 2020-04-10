package com.scor.rr.domain.entities.ScopeAndCompleteness.Views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vw_Imported_PLTs_ExpectedScope")
public class PricedScopeAndCompletenessView {

    @Id
    private long pLTHeaderId;

    private String defaultPltName;

    private String perilCode;

    private long projectId;

    private String regionPerilCode;

    private String regionPerilDesc;

    private String grain;

    private int division;

    private String accumulationRapCode;



}
