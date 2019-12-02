package com.scor.rr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "vw_Treaty")
public class TreatyView {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "label")
    private String label;

    public TreatyView() {
    }

    public String getId() {
        return id;
    }

    public void setId(String treatyId) {
        this.id = treatyId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String treatyLabel) {
        this.label = treatyLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreatyView treatyView = (TreatyView) o;
        return Objects.equals(id, treatyView.id) &&
                Objects.equals(label, treatyView.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label);
    }

}
