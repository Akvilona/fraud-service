package com.froud.fraudservice.controller;

import com.froud.fraudservice.entity.FraudUser;
import com.froud.fraudservice.entity.FraudUsersEntity;
import com.froud.fraudservice.mapper.FraudUserMapper;
import com.froud.fraudservice.service.FraudUserApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FraudUserApiController implements FraudUserApi {
    private final FraudUserApiService fraudUserApiService;
    private final FraudUserMapper fraudUserMapper;

    @Override
    public ResponseEntity<FraudUser> createFraudUser(final FraudUser fraudUser) {
        final FraudUsersEntity fraudUsersEntity = fraudUserMapper.mapToFraudUserEntity(fraudUser);
        fraudUserApiService.createFraudUser(fraudUsersEntity);
        final FraudUser fraudUserResponse = fraudUserMapper.mapToFraudUser(fraudUsersEntity);
        return ResponseEntity.ok(fraudUserResponse);
    }

    @Override
    public ResponseEntity<List<FraudUser>> getAllFraudUsers() {
        final List<FraudUser> fraudUsersList = fraudUserApiService.getAll().stream()
            .map(fraudUserMapper::mapToFraudUser)
            .toList();
        return ResponseEntity.ok(fraudUsersList);
    }

    @Override
    public ResponseEntity<FraudUser> getFraudUserById(final Long id) {
        final FraudUsersEntity fraudUsersEntity = fraudUserApiService.findById(id);
        final FraudUser fraudUser = fraudUserMapper.mapToFraudUser(fraudUsersEntity);
        return ResponseEntity.ok(fraudUser);
    }

    @Override
    public ResponseEntity<FraudUser> updateFraudUserById(final Long id, final FraudUser fraudUser) {
        final FraudUsersEntity fraudUsersEntity = fraudUserApiService.updateUser(id, fraudUser);
        final FraudUser fraudUser1 = fraudUserMapper.mapToFraudUser(fraudUsersEntity);
        return ResponseEntity.ok(fraudUser1);
    }

    @Override
    public ResponseEntity<Void> deleteFraudUserById(final Long id) {
        fraudUserApiService.deleteById(id);
        return ResponseEntity.ok(null);
    }
}
