package com.scor.rr.domain.entities.Search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ZZ_ShortCut")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortCut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shortCutId;

    private String shortCutLabel;

    private String shortCutAttribute;

    private String mappingTable;

    private String type;


}
