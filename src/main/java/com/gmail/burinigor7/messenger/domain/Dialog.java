package com.gmail.burinigor7.messenger.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "dialogs")
@NoArgsConstructor
public class Dialog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user1", referencedColumnName = "id")
    private User user1;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user2", referencedColumnName = "id")
    private User user2;

    public Dialog(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }
}
