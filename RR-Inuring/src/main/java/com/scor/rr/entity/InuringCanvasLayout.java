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
@Table(name = "InuringCanvasLayout")
public class InuringCanvasLayout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InuringCanvasLayoutId", nullable = false)
    private long inuringCanvasLayoutId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "InuringPackageId", nullable = false)
    private long inuringPackageId;

    @Column(name = "NodeId")
    private long nodeId;

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
    private long createdBy;

    @Column(name = "Created")
    private Date created;

    @Column(name = "UpdatedBy")
    private long updatedBy;

    @Column(name = "Updated")
    private Date updated;

    public InuringCanvasLayout() {
    }
}
