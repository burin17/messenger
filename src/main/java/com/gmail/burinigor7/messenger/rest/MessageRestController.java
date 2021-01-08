package com.gmail.burinigor7.messenger.rest;

import com.gmail.burinigor7.messenger.domain.Message;
import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

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
                        @RequestParam String text, @RequestBody MultipartFile file) throws IOException {
        return messageService.saveMessage(recipient, text, file);
    }

    @GetMapping("/file/{fileName}")
    public ResponseEntity<Resource> file(@PathVariable String fileName) throws FileNotFoundException {
        Resource file = messageService.file(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + '\"')
                .body(file);
    }
}
