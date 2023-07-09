package com.micro-service.item.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.micro-service.item.entity.ItemEntity;

public interface ItemRepo extends JpaRepository<ItemEntity, Long> {

}
