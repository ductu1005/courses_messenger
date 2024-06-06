package com.datn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatMessage {
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private StatusMessage status;
}
