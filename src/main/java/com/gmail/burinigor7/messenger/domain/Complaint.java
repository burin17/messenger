package com.gmail.burinigor7.messenger.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "complaints")
@NoArgsConstructor
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "target", referencedColumnName = "id")
    private User target;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "holder", referencedColumnName = "id")
    private User holder;

    public Complaint(User holder, User target) {
        this.holder = holder;
        this.target = target;
    }
}
