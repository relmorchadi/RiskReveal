package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExposureView {

    @Id
    private Long ExposureViewId;
    @Column(name = "Name")
    private String name;
    @Column(name = "DefaultView")
    private boolean defaultView;
}
