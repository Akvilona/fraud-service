package com.froud.fraudservice.repository;

import com.froud.fraudservice.entity.FraudUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<FraudUsersEntity, Long> {
}
