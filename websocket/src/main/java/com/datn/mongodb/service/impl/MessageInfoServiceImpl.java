package com.datn.mongodb.service.impl;

import com.datn.mongodb.entity.MessagesInfoEntity;
import com.datn.mongodb.repository.MessageInfoRepository;
import com.datn.mongodb.service.MessageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageInfoServiceImpl implements MessageInfoService {

    @Autowired
    private MessageInfoRepository messageInfoRepository;

    @Override
    public MessagesInfoEntity saveMessage(MessagesInfoEntity message) {
        Long timestamp = System.currentTimeMillis();
        message.setTimestamp(timestamp);
        return messageInfoRepository.save(message);
    }

    @Override
    public List<MessagesInfoEntity> getAllMessages() {
        return messageInfoRepository.findAll();
    }

    @Override
    public List<MessagesInfoEntity> getAllMessagesByGroups(Long groupId) {
        return messageInfoRepository.findByGroupId(groupId);
    }
}
