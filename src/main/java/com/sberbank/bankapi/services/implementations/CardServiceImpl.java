package com.sberbank.bankapi.services.implementations;

import com.sberbank.bankapi.DAO.AccountDAO;
import com.sberbank.bankapi.DAO.CardDAO;
import com.sberbank.bankapi.DTO.AccountDTO;
import com.sberbank.bankapi.DTO.CardDTO;
import com.sberbank.bankapi.DTO.MessageDTO;
import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.entities.CardEntity;
import com.sberbank.bankapi.services.interfaces.AccountService;
import com.sberbank.bankapi.services.interfaces.CardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {

    private final AccountDAO accountDAO;
    private final AccountService accountService;
    private final CardDAO cardDAO;

    @Override
    public List<CardDTO> getCardsByPersonId(int personId) {
        List<CardDTO> cards = new ArrayList<>();
        List<AccountDTO> accountEntity = accountService.getAccountsByPersonId(personId);
        for (AccountDTO account : accountEntity) {
            if (cardDAO.getCardByAccountNumber(account.getAccount()) != null) {
                cards.add(transferToCardDTO(cardDAO.getCardByAccountNumber(account.getAccount())));
            }
        }
        return cards;
    }

    @Override
    public CardDTO getCardByCardNumber(String cardNumber) {
        return transferToCardDTO(cardDAO.getCardByCardNumber(cardNumber));
    }

    @Override
    public CardEntity getCardEntityByCardNumber(String cardNumber) {
        return cardDAO.getCardByCardNumber(cardNumber);
    }

    @Override
    public double getBalanceForCard(String cardNumber) {
        CardEntity cardEntity = cardDAO.getCardByCardNumber(cardNumber);
        return cardEntity.getAccountEntity().getBalance();
    }

    @Override
    public Map<Boolean, MessageDTO> createNewCard(AccountEntity accountEntity, CardEntity cardEntity) {
        Map<Boolean, MessageDTO> message = new HashMap<>();
        if (accountEntity.getRequestCard() == accountEntity.getConfirmedRequest()) {
            accountEntity.createCard(cardEntity);
            accountEntity.setRequestCard(0);
            accountEntity.setConfirmedRequest(0);
            cardDAO.saveCard(cardEntity);
            accountDAO.saveAccount(accountEntity);
            message.put(true, MessageDTO.builder().message("Карта выпущена").build());
        } else {
            message.put(false, MessageDTO.builder().message("Заявка на выпуск карты еще не подтверждена").build());
        }
        return message;
    }

    @Override
    public AccountDTO addMoneyToCard(String cardNumber, double sum) {
        CardEntity cardEntity = cardDAO.getCardByCardNumber(cardNumber);
        AccountEntity accountEntity = cardEntity.getAccountEntity();
        accountEntity.addMoneyToBalance(sum);
        accountDAO.saveAccount(accountEntity);
        return accountService.transferToAccountDTO(accountEntity);
    }

    @Override
    public AccountDTO deleteCard(String cardNumber) {
        String account = getCardByCardNumber(cardNumber).getAccountEntity().getAccount();
        cardDAO.deleteCard(cardNumber);
        return accountService.getAccountByAccountNumber(account);
    }

    @Override
    public CardDTO transferToCardDTO(CardEntity cardEntity) {
        return CardDTO.builder()
                .id(cardEntity.getId())
                .accountEntity(cardEntity.getAccountEntity())
                .number(cardEntity.getNumber())
                .state(cardEntity.getState())
                .monthUntil(cardEntity.getMonthUntil())
                .yearUntil(cardEntity.getYearUntil())
                .build();
    }
}
