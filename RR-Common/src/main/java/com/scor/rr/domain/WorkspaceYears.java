package com.scor.rr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "vw_Workspace_Years")
public class WorkspaceYears {

    @Id
    @Column(name = "workspaceUwYear")
    private String label;

    public WorkspaceYears() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String workspaceUwYear) {
        this.label = workspaceUwYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkspaceYears that = (WorkspaceYears) o;
        return Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}
