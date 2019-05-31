package com.scor.rr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "BinFile", schema = "dbo", catalog = "RiskReveal")
public class BinFileEntity {
    private int binFileId;

    @Id
    @Column(name = "BinFile_Id", nullable = false, precision = 0)
    public int getBinFileId() {
        return binFileId;
    }

    public void setBinFileId(int binFileId) {
        this.binFileId = binFileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinFileEntity that = (BinFileEntity) o;
        return binFileId == that.binFileId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(binFileId);
    }
}
