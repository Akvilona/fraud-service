package com.froud.fraudservice.controller;

import com.froud.fraudservice.entity.DepoDataDebt;
import com.froud.fraudservice.entity.DepoDataDepositor;
import com.froud.fraudservice.entity.DepoDataPay;
import com.froud.fraudservice.repository.DebtRepository;
import com.froud.fraudservice.repository.DepositorRepository;
import com.froud.fraudservice.repository.PayRepository;
import com.froud.fraudservice.server.dto.DepoDataResultInner;
import com.froud.fraudservice.support.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor
class DepoDataControllerTest extends IntegrationTestBase {

    private final DebtRepository debtRepository;
    private final PayRepository payRepository;
    private final DepositorRepository depositorRepository;
    private String dateFrom;
    private String dateTo;

    @Test
    void getDepoDataTest() {
        // инициализируем две даты dateFrom и dateTo
        createDateFromAndDateTo();
//        commandLineRunner();

    }

    public List<DepoDataResultInner> getDepoData(final String dateFrom, final String dateTo) {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateFromDate = LocalDate.parse(dateFrom, dtf);
        LocalDate dateToDate = LocalDate.parse(dateTo, dtf);

        return debtRepository.getDebtInformation(dateFromDate, dateToDate, 2000);
    }

    private static List<DepoDataPay> getPayList() {
        return List.of(
            DepoDataPay.builder()
                .billId(201)
                .amount(new BigDecimal("75.0"))
                .status("Completed")
                .build(),
            DepoDataPay.builder()
                .billId(203)
                .amount(new BigDecimal("120.0"))
                .status("Pending")
                .build(),
            DepoDataPay.builder()
                .billId(202)
                .amount(new BigDecimal("200.00"))
                .status("Completed")
                .build()
        );
    }

    private static List<DepoDataDepositor> getDepositorList() {
        return List.of(
            DepoDataDepositor.builder()
                .personalAccountNumber("ACC12345")
                .build(),
            DepoDataDepositor.builder()
                .personalAccountNumber("ACC67890")
                .build(),
            DepoDataDepositor.builder()
                .personalAccountNumber("ACC24680")
                .build()
        );
    }

    private static List<DepoDataDebt> getDebtList() {
        return List.of(
            DepoDataDebt.builder()
                .debtDepositor(101)
                .billId(201)
                .depositorType("Individual")
                .vatAmount(BigDecimal.valueOf(15.50))
                .amountWithoutVat(BigDecimal.valueOf(100.00))
                .remainder(BigDecimal.valueOf(85.50))
                .endServicePeriod(LocalDate.of(2023, 12, 31))
                .status("REGISTERED")
                .billFormedDate(LocalDate.of(2023, 12, 31))
                .build(),
            DepoDataDebt.builder()
                .debtDepositor(102)
                .billId(202)
                .depositorType("Corporate")
                .vatAmount(BigDecimal.valueOf(30.50))
                .amountWithoutVat(BigDecimal.valueOf(250.00))
                .remainder(BigDecimal.valueOf(222.50))
                .endServicePeriod(LocalDate.of(2023, 12, 15))
                .status("REGISTERED")
                .billFormedDate(LocalDate.of(2023, 12, 5))
                .build(),
            DepoDataDebt.builder()
                .debtDepositor(103)
                .billId(203)
                .depositorType("Individual")
                .vatAmount(BigDecimal.valueOf(30.50))
                .amountWithoutVat(BigDecimal.valueOf(250.00))
                .remainder(BigDecimal.valueOf(222.50))
                .endServicePeriod(LocalDate.of(2023, 12, 20))
                .status("REGISTERED")
                .billFormedDate(LocalDate.of(2023, 12, 10))
                .build()
        );
    }

    public void createDateFromAndDateTo() {
        // Получаем текущую дату
        LocalDate currentDate = LocalDate.now();

        // Форматируем даты в нужный формат, например, "yyyy-MM-dd"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Заполняем переменные
        dateFrom = currentDate.format(formatter);

        // Например, добавляем к текущей дате 7 дней для dateTo
        LocalDate endDate = currentDate.plusDays(7);
        dateTo = endDate.format(formatter);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            payRepository.saveAll(getPayList());
            depositorRepository.saveAll(getDepositorList());
            debtRepository.saveAll(getDebtList());

            getDepoData(dateFrom, dateTo);
        };
    }
}
