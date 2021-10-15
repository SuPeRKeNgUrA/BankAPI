package com.sberbank.bankapi.services.interfaces;

import com.sberbank.bankapi.DTO.AccountDTO;
import com.sberbank.bankapi.DTO.CardDTO;
import com.sberbank.bankapi.DTO.MessageDTO;
import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.entities.CardEntity;

import java.util.List;
import java.util.Map;

/**
 * Сервис для работы с данными о картах, привязанных к счетам клиентов банка, внесением денежных средств на карту, ее удалением и запросе баланса
 */
public interface CardService {

    List<CardDTO> getCardsByPersonId(int personId);

    Map<Boolean, MessageDTO> createNewCard(AccountEntity accountEntity, CardEntity cardEntity);

    CardDTO getCardByCardNumber(String cardNumber);

    CardEntity getCardEntityByCardNumber(String cardNumber);

    AccountDTO addMoneyToCard(String cardNumber, double sum);

    AccountDTO deleteCard(String cardNumber);

    double getBalanceForCard(String cardNumber);

    CardDTO transferToCardDTO(CardEntity cardEntity);

}
