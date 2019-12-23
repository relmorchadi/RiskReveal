package com.scor.rr.domain.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_RLAnalysisToTargetRAP")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RLAnalysisToTargetRAP {

    @Id
    @Column(name = "Id")
    private Long id;
    @Column(name = "RLModelAnalysisId")
    private Long rlAnalysisId;
    @Column(name = "RLDataSourceId")
    private Long rlModelDataSourceId;
    @Column(name = "AnalysisId")
    private Long analysisId;
    @Column(name = "AnalysisName")
    private String analysisName;
    @Column(name = "ProfileKey")
    private String profileKey;
    @Column(name = "TargetRapCode")
    private String targetRapCode;
    @Column(name = "TargetRapDesc")
    private String targetRapDesc;
    @Column(name = "IsSCORCurrent")
    private boolean isCurrent;
    @Column(name = "IsSCORDefault")
    private boolean isDefault;
    @Column(name = "IsSCORGenerated")
    private boolean isGenerated;

}
