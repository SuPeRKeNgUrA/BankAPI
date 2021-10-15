package com.sberbank.bankapi.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PERSONS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "passport")
    private String passport;

    @JsonManagedReference
    @OneToMany(mappedBy = "personEntity")
    @Fetch(FetchMode.JOIN)
    @Column(name = "account")
    private List<AccountEntity> account;

    @Column(name = "requestAccount")
    private int requestAccount;

    @Column(name = "confirmedRequest")
    private int confirmedRequest;

    public void createAccount(AccountEntity accountEntity) {
        if (account == null) {
            account = new ArrayList<>();
        }
        account.add(accountEntity);
        accountEntity.setPersonEntity(this);
    }

}
