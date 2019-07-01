package com.scor.rr.domain.dto.adjustement.modelcsvadjustement;

import com.scor.rr.configuration.excel.annotation.RowCell;
import com.scor.rr.configuration.excel.annotation.RowIndex;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PLTLossDataExcelModel {
    @RowIndex
    private Integer rowIndex;
    @RowCell(index = 0 )
    private int simPeriod;
    @RowCell(index = 1 )
    private int eventId;
    @RowCell(index = 2 )
    private Date eventDate;
    @RowCell(index = 3 )
    private int seq;
    @RowCell(index = 4 )
    private double maxExposure;
    @RowCell(index = 5 )
    private double loss;

    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public int getSimPeriod() {
        return simPeriod;
    }

    public void setSimPeriod(int simPeriod) {
        this.simPeriod = simPeriod;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public double getMaxExposure() {
        return maxExposure;
    }

    public void setMaxExposure(double maxExposure) {
        this.maxExposure = maxExposure;
    }

    public double getLoss() {
        return loss;
    }

    public void setLoss(double loss) {
        this.loss = loss;
    }
}
