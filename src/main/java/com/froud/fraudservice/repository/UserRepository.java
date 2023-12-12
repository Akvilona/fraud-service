package com.froud.fraudservice.repository;

import com.froud.fraudservice.entity.FraudUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<FraudUserEntity, Long> {
    Optional<FraudUserEntity> findByEmail(String email);
}
