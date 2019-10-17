package com.scor.rr.domain.entities.references.cat.message;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the MessageProcess database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "MessageProcess")
@Data
public class MessageProcess {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private String id;
    @Column(name = "Code")
    private String code;
    @Column(name = "ProcessName")
    private String processName;
    @Column(name = "DefaultMessageTitle")
    private String defaultMessageTitle;


}
