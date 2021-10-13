package com.sberbank.bankapi.services.interfaces;

import com.sberbank.bankapi.DTO.AccountDTO;
import com.sberbank.bankapi.DTO.CardDTO;
import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.entities.CardEntity;

import java.util.List;

public interface CardService {

//    List<CardEntity> getCardsByPersonId(int personId);
//
//    void createNewCard(AccountEntity accountEntity, CardEntity cardEntity);
//
//    CardEntity getCardByCardNumber(String cardNumber);
//
//    void addMoneyToCard(String cardNumber, double sum);
//
//    void deleteCard(String cardNumber);
//
//    double getBalanceForCard(String cardNumber);

    List<CardDTO> getCardsByPersonId(int personId);

    CardDTO createNewCard(AccountEntity accountEntity, CardEntity cardEntity);

    CardDTO getCardByCardNumber(String cardNumber);

    CardEntity getCardEntityByCardNumber(String cardNumber);

    AccountDTO addMoneyToCard(String cardNumber, double sum);

    AccountDTO deleteCard(String cardNumber);

    double getBalanceForCard(String cardNumber);

    CardDTO transferToCardDTO(CardEntity cardEntity);

}
