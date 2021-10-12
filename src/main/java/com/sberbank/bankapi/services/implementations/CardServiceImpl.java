package com.sberbank.bankapi.services.implementations;

import com.sberbank.bankapi.DAO.AccountDAO;
import com.sberbank.bankapi.DAO.CardDAO;
import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.entities.CardEntity;
import com.sberbank.bankapi.services.interfaces.AccountService;
import com.sberbank.bankapi.services.interfaces.CardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {

    private AccountDAO accountDAO;
    private AccountService accountService;
    private CardDAO cardDAO;

    @Override
    public List<CardEntity> getCardsByPersonId(int personId) {
        List<CardEntity> cards = new ArrayList<>();
        List<AccountEntity> accountEntity = accountService.getAccountsByPersonId(personId);
        for (AccountEntity account : accountEntity) {
            cards.add(cardDAO.getCardByAccountId(account.getId()));
        }
        return cards;
    }

    @Override
    public CardEntity getCardByCardNumber(String cardNumber) {
        return cardDAO.getCardByCardNumber(cardNumber);
    }

    @Override
    public double getBalanceForCard(String cardNumber) {
        CardEntity cardEntity = getCardByCardNumber(cardNumber);
        return cardEntity.getAccountEntity().getBalance();
    }

    @Override
    public void createNewCard(AccountEntity accountEntity, CardEntity cardEntity) {
        accountEntity.createCard(cardEntity);
        cardDAO.saveCard(cardEntity);
    }

    @Override
    public void addMoneyToCard(String cardNumber, double sum) {
        CardEntity cardEntity = getCardByCardNumber(cardNumber);
        AccountEntity accountEntity = cardEntity.getAccountEntity();
        accountEntity.addMoneyToBalance(sum);
        accountDAO.saveAccount(accountEntity);
    }

    @Override
    public void deleteCard(String cardNumber) {
        cardDAO.deleteCard(cardNumber);
    }

}
