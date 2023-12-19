package com.froud.fraudservice.repository;

import java.math.BigDecimal;

public interface DebtProjection {
    @SuppressWarnings("MethodName")
    String getDepositor_Type();

    String getBalanceAccountNumber();

    String getPersonalAccountNumber();

    BigDecimal getReminderActive();

    BigDecimal getReminderPassive();

    BigDecimal getTaxBase();

    BigDecimal getVatSumTimeDiff();

    BigDecimal getDecreaseSumTimeDiff();

    BigDecimal getSumLoss();

    BigDecimal getNdsPercent();

    BigDecimal getFinResult();

    BigDecimal getAddCapital();

    BigDecimal getFinResultVat();

    BigDecimal getAddCapital2();

    BigDecimal getSumOna();

    BigDecimal getFinResultVat2();

    BigDecimal getAddCapital3();

    BigDecimal getSumTakenLoss();

    BigDecimal getSaldo61701();

    BigDecimal getSaldo61703();

    BigDecimal getSaldo10609();
}
