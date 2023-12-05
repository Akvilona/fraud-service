package com.froud.fraudservice.repository;

import com.froud.fraudservice.entity.FraudUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<FraudUsersEntity, Long> {
    @Query("""
            SELECT u FROM FraudUsersEntity u\s
            WHERE  u.id = :userId
            """)
    Optional<FraudUsersEntity> findUserFraudInfo(@Param("userId") Long userId);
}
