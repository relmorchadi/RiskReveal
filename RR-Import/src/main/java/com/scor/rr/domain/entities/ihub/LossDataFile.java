package com.scor.rr.domain.entities.ihub;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * The persistent class for the LossDataFile database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "LossDataFile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LossDataFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "FileName")
    private String fileName;
    @Column(name = "FilePath")
    private String filePath;

}
