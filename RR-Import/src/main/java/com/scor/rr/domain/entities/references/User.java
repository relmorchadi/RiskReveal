package com.scor.rr.domain.entities.references;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * The persistent class for the ALMFUser database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ALMFUser")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private Long userId;
    @Column(name = "Code")
    private String code;
    @Column(name = "FirstName")
    private String firstName;
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "Email")
    private String eMail;
    @Column(name = "UserName")
    private String userName;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "AccessApplications")
    private String accessApplications;
    @Column(name = "CreatedDate")
    private Date createdDate;
    @Column(name = "LastUpdatedDate")
    private Date lastUpdatedDate;
    @Column(name = "UwpUserCode")
    private String uwpUserCode;
    @Column(name = "Ledger")
    private String ledger;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoleId")
    private Role role;

    /**
     * get FirstName and LastName
     *
     * @return
     */
//    public String getFirstNameAndLastName() {
//        // @formatter:off
//        return ALMFUtils.isNotNull(firstName)
//                ? firstName.concat(" ").concat(ALMFUtils.isNotNull(lastName)
//                ? lastName
//                : "")
//                : ALMFUtils.isNotNull(lastName)
//                ? lastName
//                : "";
//        // @formatter:on
//    }

}
