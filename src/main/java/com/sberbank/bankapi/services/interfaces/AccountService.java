package com.sberbank.bankapi.services.interfaces;

import com.sberbank.bankapi.entities.AccountEntity;

import java.util.List;

public interface AccountService {

    List<AccountEntity> getAccountsByPersonId(int personId);

    AccountEntity getAccountByAccountNumber(String accountNumber);

    void createNewAccount(int personId, AccountEntity accountEntity);

    List<AccountEntity> transferMoney(String accountNumberFrom, String accountNumberTo, double sum);

}
