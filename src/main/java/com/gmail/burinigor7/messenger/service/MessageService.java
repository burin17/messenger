package com.gmail.burinigor7.messenger.service;

import com.gmail.burinigor7.messenger.domain.Dialog;
import com.gmail.burinigor7.messenger.domain.Message;
import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ProfileService profileService;
    private final DialogService dialogService;
    private final String uploadsPath;

    @Autowired
    public MessageService(MessageRepository messageRepository,
                          ProfileService profileService,
                          DialogService dialogService,
                          @Value("${uploads.path}") String uploadsPath) {
        this.messageRepository = messageRepository;
        this.profileService = profileService;
        this.dialogService = dialogService;
        this.uploadsPath = uploadsPath;
    }

    public List<Message> getDialog(User recipient) {
        return messageRepository.getMessagesOfDialog(
                profileService.selfProfile().getId(),
                recipient.getId());
    }

    public List<Message> getDialog(User user1, User user2) {
        return messageRepository.getMessagesOfDialog(user1.getId(), user2.getId());
    }

    public Message saveMessage(User recipient, String text,
                               MultipartFile file) throws IOException {
        Message message = new Message(
                profileService.selfProfile(),
                recipient, text,
                dialogService.dialog(recipient)
        );
        if(file != null) {
            File uploadDir = new File(uploadsPath);
            if(!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String fileName = UUID.randomUUID().toString() + "_"
                    + file.getOriginalFilename();
            file.transferTo(new File(uploadsPath + "/" + fileName));
            message.setFileName(fileName);
        }
        return messageRepository.save(message);
    }

    public Message message(Long messageId) {
        return messageRepository.findById(messageId)
                .get();
    }

    public User selfProfile() {
        return profileService.selfProfile();
    }

    public List<User> usersWithDialog() {
        User authenticated = selfProfile();
        List<Dialog> dialogs = dialogService.findAllForUser();
        return dialogs.stream()
                .map(dialog -> dialog.getUser1().getId().equals(authenticated.getId()) ? dialog.getUser2() : dialog.getUser1())
                .collect(Collectors.toList());
    }

    public Resource file(String fileName) throws FileNotFoundException {
        return new InputStreamResource(new FileInputStream(uploadsPath + "/" + fileName));
    }
}
