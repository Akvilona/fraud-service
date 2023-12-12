package com.froud.fraudservice.service;

import com.froud.fraudservice.entity.FraudUserEntity;
import com.froud.fraudservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class FraudUserApiService {
    private final UserRepository userRepository;

    public FraudUserEntity createFraudUser(final FraudUserEntity fraudUserEntity) {
        return userRepository.save(fraudUserEntity);
    }

    public Boolean findByEmail(final String email) {
        return userRepository.findByEmail(email)
            .isPresent();
    }

    public void deleteById(final Long id) {
        userRepository.deleteById(id);
    }

}
