package com.scor.rr.domain;

import com.scor.rr.util.StringPrefixedSequenceIdGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "[RR-project]", schema = "poc")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

  @Id
  @Column(name = "_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
  @GenericGenerator(
          name = "project_seq",
          strategy = "com.scor.rr.util.StringPrefixedSequenceIdGenerator",
          parameters = {
                  @Parameter(name = SequenceStyleGenerator.INITIAL_PARAM,value = "8891"),
                  @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                  @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "P-"),
                  @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%09d") })

  private String projectId;
  private String assignedTo;
  private Boolean clonedFlag;
  private String createdBy;
  private Date creationDate=new Date();
  private String description;
  private Date dueDate;
  private Boolean isLocking;
  private Boolean linkFlag;
  private Boolean masterFlag;
  private String mgaexpectedFrequencycode;
  private String mgafinancialBasiscode;
  private Integer mgasectionId;
  private String mgasubmissionPeriod;
  private String mgatreatyId;
  private String name;
  private Double pltSum;
  private Double pltThreadSum;
  private Boolean postInuredFlag;
  private String publishFlag;
  private Date receptionDate;
  private Double regionPerilSum;
  private Double xactSum;

}
