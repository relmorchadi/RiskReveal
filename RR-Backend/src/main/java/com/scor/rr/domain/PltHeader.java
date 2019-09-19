package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"currencyCode","geoCode","perilCode","fileName",
        "pltType","project","publishToArc","regionPeril","rrAnalysisId",
        "targetRap","udName","userSelectedGrain","xActPublicationDate","xactPublicationDate","userTags"})
@Table(name = "[RR-PltHeader]", schema = "poc")
public class PltHeader {

  @Id
  @Column(name = "_id")
  private String id;
  private String currencyCode;
  private String geoCode;
  private String perilCode;
  private String fileName;
  private String pltType;
  private String project;
  private String publishToArc;
  private String regionPeril;
  private String rrAnalysisId;
  private String targetRap;
  private String udName;
  private String userSelectedGrain;
  private String xActPublicationDate;

  @OneToMany(mappedBy = "userTagPltPk.plt")
  @JsonIgnore
  Set<UserTagPlt> Assignment = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PltHeader)) return false;
    PltHeader pltHeader = (PltHeader) o;
    return getId().equals(pltHeader.getId()) &&
            Objects.equals(getCurrencyCode(), pltHeader.getCurrencyCode()) &&
            Objects.equals(getGeoCode(), pltHeader.getGeoCode()) &&
            Objects.equals(getPerilCode(), pltHeader.getPerilCode()) &&
            Objects.equals(getFileName(), pltHeader.getFileName()) &&
            Objects.equals(getPltType(), pltHeader.getPltType()) &&
            Objects.equals(getProject(), pltHeader.getProject()) &&
            Objects.equals(getPublishToArc(), pltHeader.getPublishToArc()) &&
            Objects.equals(getRegionPeril(), pltHeader.getRegionPeril()) &&
            Objects.equals(getRrAnalysisId(), pltHeader.getRrAnalysisId()) &&
            Objects.equals(getTargetRap(), pltHeader.getTargetRap()) &&
            Objects.equals(getUdName(), pltHeader.getUdName()) &&
            Objects.equals(getUserSelectedGrain(), pltHeader.getUserSelectedGrain()) &&
            Objects.equals(getXActPublicationDate(), pltHeader.getXActPublicationDate());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getCurrencyCode(), getGeoCode(), getPerilCode(), getFileName(), getPltType(), getProject(), getPublishToArc(), getRegionPeril(), getRrAnalysisId(), getTargetRap(), getUdName(), getUserSelectedGrain(), xActPublicationDate);
  }

  @Override
  public String toString() {
    return "PltHeader{" +
            "id='" + id + '\'' +
            ", currencyCode='" + currencyCode + '\'' +
            ", geoCode='" + geoCode + '\'' +
            ", perilCode='" + perilCode + '\'' +
            ", fileName='" + fileName + '\'' +
            ", pltType='" + pltType + '\'' +
            ", project='" + project + '\'' +
            ", publishToArc='" + publishToArc + '\'' +
            ", regionPeril='" + regionPeril + '\'' +
            ", rrAnalysisId='" + rrAnalysisId + '\'' +
            ", targetRap='" + targetRap + '\'' +
            ", udName='" + udName + '\'' +
            ", userSelectedGrain='" + userSelectedGrain + '\'' +
            ", xActPublicationDate='" + xActPublicationDate + '\'' +
            '}';
  }
}
