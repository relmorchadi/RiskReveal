package com.scor.rr.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by Soufiane Izend on 01/10/2019.
 */

@Entity
@Data
@Table(name = "InuringCanvasLayout", schema = "dbo", catalog = "RiskReveal")
public class InuringCanvasLayout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "InuringCanvasLayoutId", nullable = false)
    private int inuringCanvasLayoutId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "InuringPackageId", nullable = false)
    private int inuringPackageId;

    @Column(name = "NodeId")
    private int nodeId;

    @Column(name = "NodeType")
    private String nodeType;

    @Column(name = "NodeName")
    private String nodeName;

    @Column(name = "NodeDisplayName")
    private String nodeDisplayName;

    @Column(name = "NodeTop")
    private BigDecimal nodeTop;

    @Column(name = "NodeLeft")
    private BigDecimal nodeLeft;

    @Column(name = "CreatedBy")
    private int createdBy;

    @Column(name = "Created")
    private Date created;

    @Column(name = "UpdatedBy")
    private int updatedBy;

    @Column(name = "Updated")
    private Date updated;

    public InuringCanvasLayout() {
    }
}
