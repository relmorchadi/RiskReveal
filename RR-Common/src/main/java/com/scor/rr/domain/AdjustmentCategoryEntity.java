package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentCategory")
public class AdjustmentCategoryEntity {
    private int adjustmentCategoryId;
    private String code;
    private String categoryName;
    private String categoryDesc;

    @Id
    @Column(name = "AdjustmentCategoryId", nullable = false)
    public int getAdjustmentCategoryId() {
        return adjustmentCategoryId;
    }

    public void setAdjustmentCategoryId(int adjustmentCategoryId) {
        this.adjustmentCategoryId = adjustmentCategoryId;
    }

    @Basic
    @Column(name = "Code", nullable = true, length = 200)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "CategoryName", nullable = true, length = 200)
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Basic
    @Column(name = "CategoryDesc", nullable = true, length = 800)
    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentCategoryEntity that = (AdjustmentCategoryEntity) o;
        return adjustmentCategoryId == that.adjustmentCategoryId &&
                Objects.equals(code, that.code) &&
                Objects.equals(categoryName, that.categoryName) &&
                Objects.equals(categoryDesc, that.categoryDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentCategoryId, code, categoryName, categoryDesc);
    }
}
