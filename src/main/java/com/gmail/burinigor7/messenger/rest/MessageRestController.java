package com.gmail.burinigor7.messenger.rest;

import com.gmail.burinigor7.messenger.domain.Message;
import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/dialog")
public class MessageRestController {
    private final MessageService messageService;

    @Autowired
    public MessageRestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send/{id}")
    public Message send(@PathVariable("id") User recipient,
                        @RequestParam String text,
                        Principal principal) {
        return messageService.saveMessage(recipient, principal, text);
    }
}
