package com.gmail.burinigor7.messenger.controller;

import com.gmail.burinigor7.messenger.domain.Message;
import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String dialogPage(@PathVariable("id") User recipient, Model model) {
        if(recipient.getId().equals(messageService.selfProfile().getId())) {
            return "redirect:/profile/self";
        }
        User sender = messageService.selfProfile();
        List<Message> dialog = messageService.getDialog(recipient);
        model.addAttribute("messages", dialog);
        model.addAttribute("sender", sender);
        model.addAttribute("recipient", recipient);
        return "dialog";
    }

    @PostMapping("/send/{id}")
    public String sendAction(@PathVariable("id") User recipient,
                             String text,
                             Model model) {
        messageService.saveMessage(recipient, text);
        return dialogPage(recipient, model);
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("ivan"));
    }
}
