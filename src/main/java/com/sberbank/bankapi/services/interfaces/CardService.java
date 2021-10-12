package com.sberbank.bankapi.services.interfaces;

import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.entities.CardEntity;

import java.util.List;

public interface CardService {

    List<CardEntity> getCardsByPersonId(int personId);

    void createNewCard(AccountEntity accountEntity, CardEntity cardEntity);

    CardEntity getCardByCardNumber(String cardNumber);

    void addMoneyToCard(String cardNumber, double sum);

    void deleteCard(String cardNumber);

    double getBalanceForCard(String cardNumber);

}
