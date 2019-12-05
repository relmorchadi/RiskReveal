package com.scor.rr.domain.entities.Search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ShortCut")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortCut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shortCutId;

    @Column(unique = true)
    private String shortCutLabel;

    @Column(unique = true)
    private String shortCutAttribute;

    @Column(unique = true, nullable = false, updatable = false)
    private String mappingTable;

}
