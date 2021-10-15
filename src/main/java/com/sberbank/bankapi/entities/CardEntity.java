package com.sberbank.bankapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "CARDS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    @Column(name = "number")
    private String number;

    @Column(name = "state")
    private String state;

    @Column(name = "month_until")
    private int monthUntil;

    @Column(name = "year_until")
    private int yearUntil;

}
