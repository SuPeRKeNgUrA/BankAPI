package com.sberbank.bankapi.DTO;

import com.sberbank.bankapi.entities.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private int id;
    private String name;
    private String surname;
    private String phone;
    private String passport;
    private List<AccountEntity> account;
    private int requestAccount;

}
