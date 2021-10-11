package com.sberbank.bankapi.services;

import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.entities.CardEntity;
import com.sberbank.bankapi.entities.PersonEntity;

import java.util.List;

public interface PersonService {

    public PersonEntity getPersonById(int personId);
    public List<AccountEntity> getAccountsByPersonId(int personId);
    public List<CardEntity> getCardsByPersonId(int personId);
    public AccountEntity getAccountByAccountNumber(String accountNumber);
    public void createNewAccount(int personId, AccountEntity accountEntity);
    public void createNewCard(AccountEntity accountEntity, CardEntity cardEntity);
    public CardEntity getCardByCardNumber(String cardNumber);
    public void createPerson(PersonEntity personEntity);
    public List<PersonEntity> getAllPersons();
    public void addMoneyToCard(String cardNumber, double sum);
    public void deleteCard(String cardNumber);
    public double getBalanceForCard(String cardNumber);

}
