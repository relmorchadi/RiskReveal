package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "UserRR", schema = "dbo", catalog = "RiskReveal")
public class UserRrEntity {
    private int userId;
    private String userFirstName;
    private String userLastName;
    private String userRole;

    @Id
    @Column(name = "userId", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "userFirstName", length = 255)
    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    @Basic
    @Column(name = "userLastName", length = 255)
    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    @Basic
    @Column(name = "userRole", length = 255)
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRrEntity that = (UserRrEntity) o;
        return userId == that.userId &&
                Objects.equals(userFirstName, that.userFirstName) &&
                Objects.equals(userLastName, that.userLastName) &&
                Objects.equals(userRole, that.userRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userFirstName, userLastName, userRole);
    }
}
