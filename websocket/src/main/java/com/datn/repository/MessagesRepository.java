package com.datn.repository;

import com.datn.entity.MessagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<MessagesEntity, Long> {
    List<MessagesEntity> findByGroupId(Long groupId);
}

