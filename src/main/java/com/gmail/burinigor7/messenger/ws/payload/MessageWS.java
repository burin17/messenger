package com.gmail.burinigor7.messenger.ws.payload;

import lombok.Data;

@Data
public class MessageWS {
    private Long senderId;
    private Long recipientId;
    private String text;
}
