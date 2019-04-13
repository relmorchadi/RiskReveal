package com.rr.riskreveal.domain.counter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "COUNTRY_COUNT_VIEW", schema = "dbo", catalog = "RR")
public class CountryCountView {
    
    @Id
    @Column(name = "label")
    private String label;

    @Column(name = "count_occur")
    private Integer countOccur;

    public CountryCountView() {
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
        CountryCountView that = (CountryCountView) o;
        return Objects.equals(countOccur, that.countOccur) &&
                Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countOccur, label);
    }
}
