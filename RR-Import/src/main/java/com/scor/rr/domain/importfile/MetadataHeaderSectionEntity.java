package com.scor.rr.domain.importfile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "MetadataHeaderSection")
@Data @NoArgsConstructor @EqualsAndHashCode
public class MetadataHeaderSectionEntity {
    public static final String MANDATORY_Y = "Y";
    public static final String MANDATORY_N = "N";
    public static final String MANDATORY_D = "D";

    @Id
    @Column(name = "MetadataHeaderSectionId", nullable = false)
    private String id;
    @Column(name = "Align")
    private String align;
    @Column(name = "MetadataAttribute")
    private String metadataAttribute;
    @Column(name = "Description")
    private String description;
    @Column(name = "DataType")
    private String dataType;
    @Column(name = "Mandatory")
    private String mandatory;
    @Column(name = "Format")
    private String format;
    @Column(name = "AssertValue")
    private String assertValue;
    @Column(name = "DefaultValue")
    private String defaultValue;
}
