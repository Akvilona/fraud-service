package com.froud.fraudservice.service;

import com.froud.fraudservice.entity.FraudUser;
import com.froud.fraudservice.entity.FraudUsersEntity;
import com.froud.fraudservice.mapper.FraudUserMapper;
import com.froud.fraudservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class FraudUserApiService {
    private final UserRepository userRepository;
    private final FraudUserMapper fraudUserMapper;

    public void createFraudUser(final FraudUsersEntity fraudUsersEntity) {
        userRepository.save(fraudUsersEntity);
    }

    public List<FraudUsersEntity> getAll() {
        return userRepository.findAll();
    }

    public FraudUsersEntity findById(final Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Transactional
    public FraudUsersEntity updateUser(final Long id, final FraudUser fraudUser) {
        final FraudUsersEntity users = userRepository.findById(id).orElseThrow();
        fraudUserMapper.updateFraudUserEntity(users, fraudUser);
        return users;
    }

    public void deleteById(final Long id) {
        userRepository.deleteById(id);
    }

}
