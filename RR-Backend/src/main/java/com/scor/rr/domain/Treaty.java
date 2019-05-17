package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TREATY_VIEW", schema = "dbo", catalog = "RR")
public class Treaty {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "label")
    private String label;

    public Treaty() {
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
        Treaty treaty = (Treaty) o;
        return Objects.equals(id, treaty.id) &&
                Objects.equals(label, treaty.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label);
    }

}
