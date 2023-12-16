package com.froud.fraudservice.repository;

import com.froud.fraudservice.entity.DepoDataDepositor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositorRepository extends JpaRepository<DepoDataDepositor, Integer> {
}
