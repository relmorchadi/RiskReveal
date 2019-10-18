package com.scor.rr.domain.views;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_FAC_TREATY", schema = "poc")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VwFacTreaty {

  @Id
  private String id;
  private String analysisName;
  private String analysisCtrBusinessType;
  private String analysisCtrId;
  private Boolean analysisCtrEndorsementNmber;
  private String analysisCtrFacNumber;
  private String analysisCtrInsured;
  private String analysisCtrLabel;
  private Long analysisCtrLob;
  private Boolean analysisCtrOrderNumber;
  private Long analysisCtrSubsidiary;
  private Integer analysisCtrYear;
  private String assignDate;
  private String assignedTo;
  // private String lastUpdateDate;
  private String lastUpdatedBy;
  private String modellingSystemInstance;

}
