package com.gmail.burinigor7.messenger.repository;

import com.gmail.burinigor7.messenger.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value =  "select * " +
            "from messages m " +
            "where (m.recipient_id = :r and m.sender_id = :s) " +
            "or (m.sender_id = :r and m.recipient_id = :s)",
            nativeQuery = true)
    List<Message> getMessagesOfDialog(@Param("s") Long senderId,
                                      @Param("r") Long recipientId);
}
