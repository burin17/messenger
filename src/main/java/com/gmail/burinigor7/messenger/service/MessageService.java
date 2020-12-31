package com.gmail.burinigor7.messenger.service;

import com.gmail.burinigor7.messenger.domain.Dialog;
import com.gmail.burinigor7.messenger.domain.Message;
import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.repository.DialogRepository;
import com.gmail.burinigor7.messenger.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ProfileService profileService;
    private final DialogRepository dialogRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository,
                          ProfileService profileService,
                          DialogRepository dialogRepository) {
        this.messageRepository = messageRepository;
        this.profileService = profileService;
        this.dialogRepository = dialogRepository;
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
        User authenticated = profileService.selfProfile();
        Dialog dialog = dialogRepository.findByUsers(authenticated, recipient);
        if(dialog == null)
            dialogRepository.save(new Dialog(authenticated, recipient));
        return messageRepository.save(
                new Message(authenticated, recipient, text));
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
        List<Dialog> dialogs = dialogRepository.findAllForUser(authenticated.getId());
        return dialogs.stream()
                .map(dialog -> dialog.getUser1().getId().equals(authenticated.getId()) ? dialog.getUser2() : dialog.getUser1())
                .collect(Collectors.toList());
    }
}
