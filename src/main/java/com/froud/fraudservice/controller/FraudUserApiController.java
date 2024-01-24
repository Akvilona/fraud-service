package com.froud.fraudservice.controller;

import com.froud.fraudservice.entity.FraudUserEntity;
import com.froud.fraudservice.mapper.FraudUserMapper;
import com.froud.fraudservice.server.controller.FraudUserApi;
import com.froud.fraudservice.server.dto.FraudUser;
import com.froud.fraudservice.service.FraudUserApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FraudUserApiController implements FraudUserApi {
    private final FraudUserApiService fraudUserApiService;
    private final FraudUserMapper fraudUserMapper;

    @Override
    public ResponseEntity<Boolean> _checkFraudUserByEmail(final String email) {
        return ResponseEntity.ok(fraudUserApiService.findByEmail(email));
    }

    @Override
    public ResponseEntity<Void> _deleteFraudUserById(final Long id) {
        fraudUserApiService.deleteById(id);
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<FraudUser> _postFraudUserById(final FraudUser fraudUser) {
        FraudUserEntity fraudUserEntity = fraudUserMapper.toFraudUserEntity(fraudUser);
        FraudUserEntity userEntity = fraudUserApiService.createFraudUser(fraudUserEntity);
        return ResponseEntity.ok(fraudUserMapper.toFraudUser(userEntity));
    }

}
