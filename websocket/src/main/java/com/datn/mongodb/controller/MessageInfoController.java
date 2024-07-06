package com.datn.mongodb.controller;

import com.datn.common.ResultResp;
import com.datn.mongodb.entity.MessagesInfoEntity;
import com.datn.mongodb.service.MessageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages-info")
public class MessageInfoController {

    @Autowired
    private MessageInfoService messageInfoService;

    @PostMapping("/add")
    public MessagesInfoEntity addMessage(@RequestBody MessagesInfoEntity message) {
        return messageInfoService.saveMessage(message);
    }

    @GetMapping("/{groupId}")
    public ResultResp getUser(@PathVariable(required = true) Long groupId) throws Exception {
        return ResultResp.success(messageInfoService.getAllMessagesByGroups(groupId));
    }

    @GetMapping("/all")
    public List<MessagesInfoEntity> getAllMessages() {
        return messageInfoService.getAllMessages();
    }
}
