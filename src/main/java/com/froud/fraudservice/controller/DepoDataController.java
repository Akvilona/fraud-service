package com.froud.fraudservice.controller;

import com.froud.fraudservice.server.controller.DepoDataApi;
import com.froud.fraudservice.server.dto.DepoDataResultInner;
import com.froud.fraudservice.service.DebtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepoDataController implements DepoDataApi {
    private final DebtService debtService;

    @Override
    public ResponseEntity<List<DepoDataResultInner>> _getDepoData(final String dateFrom, final String dateTo) {
        return ResponseEntity.ok(debtService.getDepoData(dateFrom, dateTo));
    }
}
