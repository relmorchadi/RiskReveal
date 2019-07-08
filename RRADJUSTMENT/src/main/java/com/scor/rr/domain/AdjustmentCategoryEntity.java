package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentCategory", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentCategoryEntity {
    private int idCategory;
    private String code;
    private String categoryName;
    private String categoryDesc;

    @Id
    @Column(name = "id_category", nullable = false)
    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    @Basic
    @Column(name = "code", nullable = true, length = 200)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "category_name", nullable = true, length = 200)
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Basic
    @Column(name = "category_desc", nullable = true, length = 800)
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
        return idCategory == that.idCategory &&
                Objects.equals(code, that.code) &&
                Objects.equals(categoryName, that.categoryName) &&
                Objects.equals(categoryDesc, that.categoryDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategory, code, categoryName, categoryDesc);
    }
}
