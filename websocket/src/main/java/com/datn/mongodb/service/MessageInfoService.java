package com.datn.mongodb.service;

import com.datn.mongodb.entity.MessagesInfoEntity;

import java.util.List;

public interface MessageInfoService {

    public MessagesInfoEntity saveMessage(MessagesInfoEntity message);

    public List<MessagesInfoEntity> getAllMessages();

    public List<MessagesInfoEntity> getAllMessagesByGroups(Long groupId);
}