package com.sberbank.bankapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNTS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "account")
    private String account;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonEntity personEntity;

    @Column(name = "balance")
    private double balance;

    @JsonManagedReference
    @OneToOne(mappedBy = "accountEntity")
    private CardEntity card;

    @Column(name = "requestCard")
    private int requestCard;

    @Column(name = "confirmedRequest")
    private int confirmedRequest;

    public void addMoneyToBalance(double sum) {
        double sumForNow = this.getBalance();
        this.setBalance(sumForNow + sum);
    }

    public void createCard(CardEntity cardEntity) {
        cardEntity.setAccountEntity(this);
    }

}
