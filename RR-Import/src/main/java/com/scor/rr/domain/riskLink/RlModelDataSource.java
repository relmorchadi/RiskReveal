package com.scor.rr.domain.riskLink;


import com.scor.rr.domain.DataSource;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "RlModelDataSource")
public class RlModelDataSource {

    @Id
    @GeneratedValue
    private Integer rlModelDataSourceId;
    private Integer entity;
    private Integer projectId;
    // To define a Sequence
    private String rlDataSource;
    // To review the type with Shaun
    private String rlId;
    private String instanceName;
    private String instanceId;
    private String name;
    private String type;
    // To review the type with Shaun
    private String versionId;
    private Date dateCreated;
//
//    @Transient
//    private List<RlAnalysis> rlAnalysisList;

    public RlModelDataSource(DataSource dataSource,Integer projectId, String instanceId,String instanceName){
        this.projectId= projectId;
        this.entity=1;
        this.instanceId= instanceId;
        this.instanceName= instanceName;
        this.rlId= dataSource.getRmsId().toString();
        this.name= dataSource.getName();
        this.type = dataSource.getType();
        this.versionId = String.valueOf(dataSource.getVersionId());
        this.dateCreated= new Date(dataSource.getDateCreated());
    }


}
