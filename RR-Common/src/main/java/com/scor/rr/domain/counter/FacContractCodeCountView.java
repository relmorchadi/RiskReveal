package com.scor.rr.domain.counter;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "vw_Fac_Contract_Code_Count")
@Data
@NoArgsConstructor
public class FacContractCodeCountView {

    @Id
    @Column(name = "label")
    private String label;

    @Column(name = "count_occur")
    private Integer countOccur;
}

