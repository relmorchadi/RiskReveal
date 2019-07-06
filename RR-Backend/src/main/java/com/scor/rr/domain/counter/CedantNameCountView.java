package com.scor.rr.domain.counter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "CEDANT_NAME_CNT_VW", schema = "poc")
public class CedantNameCountView {

    @Id
    @Column(name = "label")
    private String label;

    @Column(name = "count_occur")
    private Integer countOccur;

    public CedantNameCountView() {
    }

    public Integer getCountOccur() {
        return countOccur;
    }

    public void setCountOccur(Integer countOccur) {
        this.countOccur = countOccur;
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
        CedantNameCountView that = (CedantNameCountView) o;
        return Objects.equals(countOccur, that.countOccur) &&
                Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countOccur, label);
    }
}
