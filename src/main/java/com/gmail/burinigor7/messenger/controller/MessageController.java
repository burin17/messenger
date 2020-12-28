package com.gmail.burinigor7.messenger.controller;

import com.gmail.burinigor7.messenger.domain.Message;
import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/dialog")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/{id}")
    public String dialogPage(@PathVariable("id") User recipient,
                             Principal principal, Model model) {
        User sender = messageService.selfProfile(principal);
        List<Message> dialog = messageService.getDialog(principal, recipient);
        System.out.println(dialog);
        model.addAttribute("messages", dialog);
        model.addAttribute("sender", sender);
        model.addAttribute("recipient", recipient);
        return "dialog";
    }

    @PostMapping("/send/{id}")
    public String sendAction(@PathVariable("id") User recipient,
                             Principal principal, String text,
                             Model model) {
        messageService.saveMessage(recipient, principal, text);
        return dialogPage(recipient, principal, model);
    }

}
