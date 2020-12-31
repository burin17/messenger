package com.gmail.burinigor7.messenger.repository;

import com.gmail.burinigor7.messenger.domain.Complaint;
import com.gmail.burinigor7.messenger.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    Complaint findByHolderAndTarget(User holder, User target);
}
