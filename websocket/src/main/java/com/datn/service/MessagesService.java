package com.datn.service;

import com.datn.dto.ChatMessagerDTO;
import com.datn.entity.MessagesEntity;

import java.util.List;

public interface MessagesService {
    
    List<ChatMessagerDTO> getMessageByGroupId(Long id);

    MessagesEntity saveMessage(ChatMessagerDTO message);
}
