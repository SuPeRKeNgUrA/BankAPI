package com.sberbank.bankapi.services.interfaces;

import com.sberbank.bankapi.DTO.AccountDTO;
import com.sberbank.bankapi.DTO.PersonDTO;
import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.entities.PersonEntity;

import java.util.List;

public interface AccountService {

//    List<AccountEntity> getAccountsByPersonId(int personId);
//
//    AccountEntity getAccountByAccountNumber(String accountNumber);
//
//    void createNewAccount(int personId, AccountEntity accountEntity);
//
//    List<AccountEntity> transferMoney(String accountNumberFrom, String accountNumberTo, double sum);

    List<AccountDTO> getAccountsByPersonId(int personId);

    AccountDTO getAccountByAccountNumber(String accountNumber);

    AccountEntity getAccountEntityByAccountNumber(String accountNumber);

    AccountDTO createNewAccount(int personId, AccountEntity accountEntity);

    List<AccountDTO> transferMoney(String accountNumberFrom, String accountNumberTo, double sum);

    AccountDTO transferToAccountDTO(AccountEntity accountEntity);

}
