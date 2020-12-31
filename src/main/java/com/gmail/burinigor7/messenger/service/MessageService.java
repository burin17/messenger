package com.gmail.burinigor7.messenger.service;

import com.gmail.burinigor7.messenger.domain.Message;
import com.gmail.burinigor7.messenger.ws.payload.MessageWS;
import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ProfileService profileService;

    @Autowired
    public MessageService(MessageRepository messageRepository, ProfileService profileService) {
        this.messageRepository = messageRepository;
        this.profileService = profileService;
    }

    public List<Message> getDialog(User recipient) {
        return messageRepository.getDialog(
                profileService.selfProfile().getId(),
                recipient.getId());
    }

    public List<Message> getDialog(User user1, User user2) {
        return messageRepository.getDialog(user1.getId(), user2.getId());
    }

    public Message saveMessage(User recipient, String text) {
        return messageRepository.save(
                new Message(
                        profileService.selfProfile(),
                        recipient,
                        text
                ));
    }

    public Message saveOwnMessage(MessageWS messageWS) {
        return messageRepository.save(
                new Message(
                        profileService.user(messageWS.getSenderId()),
                        profileService.user(messageWS.getRecipientId()),
                        messageWS.getText()
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
}
