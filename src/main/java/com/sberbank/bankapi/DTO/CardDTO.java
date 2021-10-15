package com.sberbank.bankapi.DTO;

import com.sberbank.bankapi.entities.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {

    private int id;
    private AccountEntity accountEntity;
    private String number;
    private String state;
    private int monthUntil;
    private int yearUntil;

}
