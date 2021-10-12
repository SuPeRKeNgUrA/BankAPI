package com.sberbank.bankapi.services.interfaces;

import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.entities.CardEntity;
import com.sberbank.bankapi.entities.PersonEntity;

import java.util.List;

public interface PersonService {

    PersonEntity getPersonById(int personId);
    List<AccountEntity> getAccountsByPersonId(int personId);
    List<CardEntity> getCardsByPersonId(int personId);
    AccountEntity getAccountByAccountNumber(String accountNumber);
    void createNewAccount(int personId, AccountEntity accountEntity);
    void createNewCard(AccountEntity accountEntity, CardEntity cardEntity);
    CardEntity getCardByCardNumber(String cardNumber);
    void createPerson(PersonEntity personEntity);
    List<PersonEntity> getAllPersons();
    void addMoneyToCard(String cardNumber, double sum);
    void deleteCard(String cardNumber);
    double getBalanceForCard(String cardNumber);
    List<AccountEntity> transferMoney(String accountNumberFrom, String accountNumberTo, double sum);

}
