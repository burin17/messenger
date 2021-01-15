package com.gmail.burinigor7.messenger.service;

import com.gmail.burinigor7.messenger.domain.Complaint;
import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {
    private final ComplaintRepository complaintRepository;

    @Autowired
    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public boolean complaint(User holder, User target) {
        if (complaintRepository.findByHolderAndTarget(holder, target) != null) {
            return false;
        }
        complaintRepository.save(new Complaint(holder, target));
        return true;
    }

    public List<Complaint> allComplaints() {
        return complaintRepository.findAll();
    }

    public void deleteComplaint(Complaint complaint) {
        complaintRepository.delete(complaint);
    }
}
