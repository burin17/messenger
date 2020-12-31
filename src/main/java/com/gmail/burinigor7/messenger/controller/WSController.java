package com.gmail.burinigor7.messenger.controller;

import com.gmail.burinigor7.messenger.domain.Message;
import com.gmail.burinigor7.messenger.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WSController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    @Autowired
    public WSController(SimpMessagingTemplate simpMessagingTemplate,
                        MessageService messageService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService = messageService;
    }

    @MessageMapping("/chat/{id}")
    public void sendMessage(@DestinationVariable Long id,
                            @Payload Long messageId) {
        System.out.println("handling send message: " + messageId + " to: " + id);
        Message saved = messageService.message(messageId);
        simpMessagingTemplate.convertAndSend("/topic/messages/" + id, saved);
    }
}
