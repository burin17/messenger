package com.gmail.burinigor7.messenger.controller;

import com.gmail.burinigor7.messenger.domain.Message;
import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.service.DialogService;
import com.gmail.burinigor7.messenger.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/dialog")
public class MessageController {
    private final MessageService messageService;
    private final DialogService dialogService;

    @Autowired
    public MessageController(MessageService messageService,
                             DialogService dialogService) {
        this.messageService = messageService;
        this.dialogService = dialogService;
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
        model.addAttribute("dialog", dialogService.dialog(recipient).getId());

        return "dialog";
    }

    @PostMapping("/send/{id}")
    public String sendAction(@PathVariable("id") User recipient,
                             @RequestParam MultipartFile file,
                             String text,
                             Model model) throws IOException {
        messageService.saveMessage(recipient, text, file);
        return dialogPage(recipient, model);
    }

    @GetMapping("/all")
    public String usersWithDialog(Model model) {
        model.addAttribute("users", messageService.usersWithDialog());
        return "dialogs";
    }
}
