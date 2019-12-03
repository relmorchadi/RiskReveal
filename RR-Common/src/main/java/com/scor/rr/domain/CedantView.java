package com.scor.rr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "vw_Cedant")
public class CedantView {

    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "label")
    private String label;

    public CedantView() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CedantView cedantView = (CedantView) o;
        return id == cedantView.id &&
                Objects.equals(label, cedantView.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label);
    }

}
