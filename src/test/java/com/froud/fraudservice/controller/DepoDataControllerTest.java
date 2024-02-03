package com.froud.fraudservice.controller;

import com.froud.fraudservice.server.dto.DepoDataResultInner;
import com.froud.fraudservice.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DepoDataControllerTest extends IntegrationTestBase {

    //todo: напрямую не вызываем (webTestClient)
    @Autowired
    private DepoDataController depoDataController;

    @Test
    void getDepoDataTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String dateFrom = LocalDate.now()
            .format(formatter);
        String dateTo = LocalDate.now().plusDays(7)
            .format(formatter);

        ResponseEntity<List<DepoDataResultInner>> responseEntity = depoDataController._getDepoData(dateFrom, dateTo);
        List<DepoDataResultInner> body = responseEntity.getBody();
        assertThat(body).hasSize(0);

    }

    public List<DepoDataResultInner> getDepoData(final String dateFrom, final String dateTo) {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateFromDate = LocalDate.parse(dateFrom, dtf);
        LocalDate dateToDate = LocalDate.parse(dateTo, dtf);

        return debtRepository.getDebtInformation(dateFromDate, dateToDate, 2000);
    }

}
