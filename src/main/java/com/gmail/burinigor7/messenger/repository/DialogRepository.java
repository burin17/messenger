package com.gmail.burinigor7.messenger.repository;

import com.gmail.burinigor7.messenger.domain.Dialog;
import com.gmail.burinigor7.messenger.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DialogRepository extends JpaRepository<Dialog, Long> {
    @Query(value =  "select * " +
                    "from dialogs d " +
                    "where (d.user1 = :user1 and d.user2 = :user2) " +
                    "or (d.user2 = :user1 and d.user1 = :user2)",
            nativeQuery = true)
    Dialog findByUsers(@Param("user1") User user1,
                       @Param("user2") User user2);
}
