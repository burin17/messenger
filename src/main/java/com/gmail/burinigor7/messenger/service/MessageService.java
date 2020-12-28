package com.gmail.burinigor7.messenger.service;

import com.gmail.burinigor7.messenger.domain.Message;
import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
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

    public List<Message> getDialog(Principal principal,
                         User recipient) {
        return messageRepository.getDialog(
                profileService.selfProfile(principal).getId(),
                recipient.getId());
    }

    public Message saveMessage(User recipient,
                            Principal principal,
                            String text) {
        return messageRepository.save(
                new Message(
                        profileService.selfProfile(principal),
                        recipient,
                        text
                ));
    }

    public User selfProfile(Principal principal) {
        return profileService.selfProfile(principal);
    }
}
