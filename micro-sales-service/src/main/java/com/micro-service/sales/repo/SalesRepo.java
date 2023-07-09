package com.micro-service.sales.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.micro-service.sales.entity.SalesEntity;

public interface SalesRepo extends JpaRepository<SalesEntity, Long> {

	SalesEntity findBySaleId(Long userId);
}
