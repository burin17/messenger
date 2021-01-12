package com.gmail.burinigor7.messenger.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    private User recipient;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @Column(name = "send_date")
    private Date sendDate;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "dialog_id")
    private Dialog dialog;

    @Column(name = "file_name")
    private String fileName;

    private String originalFileName;

    public Message(User sender, User recipient,
                   String text, Dialog dialog) {
        this.sender = sender;
        this.recipient = recipient;
        this.text = text;
        this.dialog = dialog;
    }

    public String getDate() {
        return new SimpleDateFormat("hh:mm")
                .format(sendDate);
    }

    public String getOriginalFileName() {
        if (fileName != null) {
            return fileName.substring(fileName.indexOf('_') + 1);
        }
        return null;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        this.originalFileName = fileName.substring(fileName.indexOf('_') + 1);
    }

    @PrePersist
    public void prePersistActions() {
        sendDate = new Date();
    }


}
