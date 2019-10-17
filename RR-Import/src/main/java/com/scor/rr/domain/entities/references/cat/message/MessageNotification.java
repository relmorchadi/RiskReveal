package com.scor.rr.domain.entities.references.cat.message;

import com.scor.rr.domain.entities.references.User;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * The persistent class for the MessageNotification database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "MessageNotification")
@Data
public class MessageNotification {
    @Id
    @Column(name = "MessageNotificationId")
    private String messageNotificationId;
    @Column(name = "Code")
    private String code;
    @Column(name = "EntityType")
    private String entityType;
    @Column(name = "EntityKey")
    private String entityKey;
    @Column(name = "EntityData")
    private String entityData;
    @Column(name = "CreatedDate")
    private Date createdDate;
    @Column(name = "Description")
    private String description;
    @Column(name = "CatRequestId")
    private String catRequestId;
    @Column(name = "InsuredName")
    private String insuredName;
    @Column(name = "UWYear")
    private String uwYear;
    @Column(name = "Read")
    private Boolean read;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CreatedBy")
    private User createdUser;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MessageStatusId")
    private MessageStatus messageStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MessageProcessId")
    private MessageProcess messageProcess;
}
