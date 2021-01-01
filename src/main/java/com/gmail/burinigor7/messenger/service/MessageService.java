package com.gmail.burinigor7.messenger.service;

import com.gmail.burinigor7.messenger.domain.Dialog;
import com.gmail.burinigor7.messenger.domain.Message;
import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ProfileService profileService;
    private final DialogService dialogService;

    @Autowired
    public MessageService(MessageRepository messageRepository,
                          ProfileService profileService,
                          DialogService dialogService) {
        this.messageRepository = messageRepository;
        this.profileService = profileService;
        this.dialogService = dialogService;
    }

    public List<Message> getDialog(User recipient) {
        return messageRepository.getMessagesOfDialog(
                profileService.selfProfile().getId(),
                recipient.getId());
    }

    public List<Message> getDialog(User user1, User user2) {
        return messageRepository.getMessagesOfDialog(user1.getId(), user2.getId());
    }

    public Message saveMessage(User recipient, String text) {
        return messageRepository.save(
                new Message(
                        profileService.selfProfile(),
                        recipient, text,
                        dialogService.dialog(recipient)
                )
        );
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
}
