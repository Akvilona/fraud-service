package com.froud.fraudservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Accessors(chain = true)
@Getter
@Setter
@ToString

@Entity
@Table(name = "debt", schema = "depo_data")
public class DepoDataDebt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "debt_depositor")
    private int debtDepositor;

    @Column(name = "bill_id")
    private int billId;

    @Column(name = "depositor_type")
    private String depositorType;

    @Column(name = "vat_amount")
    private BigDecimal vatAmount;

    @Column(name = "amount_without_vat")
    private BigDecimal amountWithoutVat;

    private BigDecimal remainder;

    @Column(name = "end_service_period")
    @Temporal(TemporalType.DATE)
    private LocalDate endServicePeriod;

    private String status;

    @Column(name = "bill_formed_date")
    @Temporal(TemporalType.DATE)
    private LocalDate billFormedDate;
}
