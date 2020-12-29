package com.gmail.burinigor7.messenger.domain;

import lombok.Data;

@Data
public class MessageWSPayload {
    private Long senderId;
    private Long recipientId;
    private String text;
}
