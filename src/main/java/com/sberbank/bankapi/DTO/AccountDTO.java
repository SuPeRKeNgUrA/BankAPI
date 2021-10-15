package com.sberbank.bankapi.DTO;

import com.sberbank.bankapi.entities.CardEntity;
import com.sberbank.bankapi.entities.PersonEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    private int id;
    private String account;
    private PersonEntity personEntity;
    private double balance;
    private CardEntity card;
    private int requestCard;
    private int confirmedRequest;

}
