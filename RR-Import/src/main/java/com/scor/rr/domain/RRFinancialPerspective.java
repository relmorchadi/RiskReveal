package com.scor.rr.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "RRFinancialPerspective")
@Data
public class RRFinancialPerspective {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String code;
    private String fullCode;
    private Long displayCode;

    // TODO : review the rest with shaun and viet
}
