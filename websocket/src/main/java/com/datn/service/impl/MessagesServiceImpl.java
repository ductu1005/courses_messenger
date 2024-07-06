package com.datn.service.impl;

import com.datn.dto.ChatMessagerDTO;
import com.datn.entity.MessagesEntity;
import com.datn.repository.MessagesRepository;
import com.datn.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessagesServiceImpl implements MessagesService {

    @Autowired
    MessagesRepository messagesRepository;


    @Override
    public List<ChatMessagerDTO> getMessageByGroupId(Long id) {
        List<MessagesEntity> entities = messagesRepository.findByGroupId(id);
        List<ChatMessagerDTO> list = new ArrayList<>();
        if (!entities.isEmpty()) {
            for (MessagesEntity entity : entities) {
                ChatMessagerDTO chatMessagerDTO = new ChatMessagerDTO();
                chatMessagerDTO.setMessage(entity.getMessage());
                chatMessagerDTO.setSenderName(entity.getSender());
                chatMessagerDTO.setTimestamp(entity.getTimestamp());
                list.add(chatMessagerDTO);
            }
            return list;
        }
        return null;
    }

    @Override
    public MessagesEntity saveMessage(ChatMessagerDTO message) {
        try {
            MessagesEntity entity = new MessagesEntity();
            entity.setMessage(message.getMessage());
            entity.setSender(message.getSenderName());
            entity.setGroupId(message.getCourseId());
            entity.setTimestamp(System.currentTimeMillis());
            messagesRepository.save(entity);
            return entity;
        } catch (Exception e) {
            throw e;
        }
    }
}
