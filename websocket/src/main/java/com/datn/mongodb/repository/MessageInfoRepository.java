package com.datn.mongodb.repository;

import com.datn.mongodb.entity.MessagesInfoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageInfoRepository extends MongoRepository<MessagesInfoEntity, String> {

    List<MessagesInfoEntity> findByGroupId(Long groupId);
}
