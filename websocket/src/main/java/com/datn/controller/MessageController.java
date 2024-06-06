package com.datn.controller;

import com.datn.common.ResultResp;
import com.datn.dto.ChatMessagerDTO;
import com.datn.entity.MessagesEntity;
import com.datn.repository.MessagesRepository;
import com.datn.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages-chat")
public class MessageController {

    @Autowired
    private MessagesService messagesService;

    @GetMapping("/{groupId}")
    public ResultResp getMessagesByGroupId(@PathVariable Long groupId) {
        return ResultResp.success(messagesService.getMessageByGroupId(groupId));
    }

    @PostMapping("/{groupId}")
    public ResultResp saveMessage(@PathVariable Long groupId,
                                  @RequestBody ChatMessagerDTO message) {
        message.setCourseId(groupId);
        return ResultResp.success(messagesService.saveMessage(message));
    }
}

