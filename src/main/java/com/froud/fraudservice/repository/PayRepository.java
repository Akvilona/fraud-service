package com.froud.fraudservice.repository;

import com.froud.fraudservice.entity.DepoDataPay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository extends JpaRepository<DepoDataPay, Integer> {
}
