package com.froud.fraudservice.service;

import com.froud.fraudservice.entity.DepoDataResultInner;
import com.froud.fraudservice.repository.DebtRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DebtService {
    private final DebtRepository debtRepository;
    //    private final DepoDataMapper depoDataMapper;
    //    private final PayRepository payRepository;
    //    private final DepositorRepository depositorRepository;

    public List<DepoDataResultInner> getDepoData(final String dateFrom, final String dateTo) {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateFromDate = LocalDate.parse(dateFrom, dtf);
        LocalDate dateToDate = LocalDate.parse(dateTo, dtf);

        return debtRepository.getDebtInformation(dateFromDate, dateToDate, 2000);
    }

    //    private static List<DepoDataPay> getPayList() {
    //        return List.of(
    //            DepoDataPay.builder()
    //                .billId(201)
    //                .amount(new BigDecimal("75.0"))
    //                .status("Completed")
    //                .build(),
    //            DepoDataPay.builder()
    //                .billId(203)
    //                .amount(new BigDecimal("120.0"))
    //                .status("Pending")
    //                .build(),
    //            DepoDataPay.builder()
    //                .billId(202)
    //                .amount(new BigDecimal("200.00"))
    //                .status("Completed")
    //                .build()
    //        );
    //    }
    //
    //    private static List<DepoDataDepositor> getDepositorList() {
    //        return List.of(
    //            DepoDataDepositor.builder()
    //                .personalAccountNumber("ACC12345")
    //                .build(),
    //            DepoDataDepositor.builder()
    //                .personalAccountNumber("ACC67890")
    //                .build(),
    //            DepoDataDepositor.builder()
    //                .personalAccountNumber("ACC24680")
    //                .build()
    //        );
    //    }
    //
    //    private static List<DepoDataDebt> getDebtList() {
    //        return List.of(
    //            DepoDataDebt.builder()
    //                .debtDepositor(101)
    //                .billId(201)
    //                .depositorType("Individual")
    //                .vatAmount(BigDecimal.valueOf(15.50))
    //                .amountWithoutVat(BigDecimal.valueOf(100.00))
    //                .remainder(BigDecimal.valueOf(85.50))
    //                .endServicePeriod(LocalDate.of(2023, 12, 31))
    //                .status("REGISTERED")
    //                .billFormedDate(LocalDate.of(2023, 12, 31))
    //                .build(),
    //            DepoDataDebt.builder()
    //                .debtDepositor(102)
    //                .billId(202)
    //                .depositorType("Corporate")
    //                .vatAmount(BigDecimal.valueOf(30.50))
    //                .amountWithoutVat(BigDecimal.valueOf(250.00))
    //                .remainder(BigDecimal.valueOf(222.50))
    //                .endServicePeriod(LocalDate.of(2023, 12, 15))
    //                .status("REGISTERED")
    //                .billFormedDate(LocalDate.of(2023, 12, 5))
    //                .build(),
    //            DepoDataDebt.builder()
    //                .debtDepositor(103)
    //                .billId(203)
    //                .depositorType("Individual")
    //                .vatAmount(BigDecimal.valueOf(30.50))
    //                .amountWithoutVat(BigDecimal.valueOf(250.00))
    //                .remainder(BigDecimal.valueOf(222.50))
    //                .endServicePeriod(LocalDate.of(2023, 12, 20))
    //                .status("REGISTERED")
    //                .billFormedDate(LocalDate.of(2023, 12, 10))
    //                .build()
    //        );
    //    }

    //        @Bean
    //        public CommandLineRunner commandLineRunner() {
    //            return args -> {
    //                payRepository.saveAll(getPayList());
    //                depositorRepository.saveAll(getDepositorList());
    //                debtRepository.saveAll(getDebtList());
    //
    //                getDepoData(dateFrom, dateTo);
    //            };
    //        }
}
