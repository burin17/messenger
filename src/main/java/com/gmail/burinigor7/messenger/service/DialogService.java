package com.gmail.burinigor7.messenger.service;

import com.gmail.burinigor7.messenger.domain.Dialog;
import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.repository.DialogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DialogService {
    private final DialogRepository dialogRepository;
    private final ProfileService profileService;

    @Autowired
    public DialogService(DialogRepository dialogRepository,
                         ProfileService profileService) {
        this.profileService = profileService;
        this.dialogRepository = dialogRepository;
    }

    public Dialog dialog(User recipient) {
        User authenticated = profileService.selfProfile();
        Dialog dialog = dialogRepository.findByUsers(authenticated, recipient);
        if(dialog == null)
            return dialogRepository.save(new Dialog(authenticated, recipient));
        return dialog;
    }

    public List<Dialog> findAllForUser() {
        return dialogRepository.findAllForUser(profileService.selfProfile().getId());
    }
}
