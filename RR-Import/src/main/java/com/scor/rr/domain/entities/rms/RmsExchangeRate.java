package com.scor.rr.domain.entities.rms;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * The persistent class for the RmsExchangeRate database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RmsExchangeRate")
@Data
public class RmsExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RmsExchangeRateId")
    private Long rmsExchangeRateId;
    @Column(name = "Ccy")
    private String ccy;
    @Column(name = "ExchangeRate")
    private Double exchangeRate;
    @Column(name = "Date")
    private Date date;
}
