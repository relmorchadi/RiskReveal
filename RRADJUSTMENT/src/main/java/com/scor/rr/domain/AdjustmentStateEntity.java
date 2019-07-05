package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentState", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentStateEntity {
    private int idState;
    private String code;
    private String stateName;
    private String stateDesc;

    @Id
    @Column(name = "id_state", nullable = false)
    public int getIdState() {
        return idState;
    }

    public void setIdState(int idState) {
        this.idState = idState;
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
    @Column(name = "state_name", nullable = true, length = 200)
    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @Basic
    @Column(name = "state_desc", nullable = true, length = 800)
    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentStateEntity that = (AdjustmentStateEntity) o;
        return idState == that.idState &&
                Objects.equals(code, that.code) &&
                Objects.equals(stateName, that.stateName) &&
                Objects.equals(stateDesc, that.stateDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idState, code, stateName, stateDesc);
    }
}
