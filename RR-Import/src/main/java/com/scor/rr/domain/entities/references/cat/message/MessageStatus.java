package com.scor.rr.domain.entities.references.cat.message;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the MessageStatus database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "MessageStatus")
@Data
public class MessageStatus {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private String id;
    @Column(name = "Code")
    private String code;
    @Column(name = "Description")
    private String description;

}
