package com.sberbank.bankapi.services;

import com.sberbank.bankapi.DAO.AccountDAO;
import com.sberbank.bankapi.DAO.CardDAO;
import com.sberbank.bankapi.DAO.PersonDAO;
import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.entities.CardEntity;
import com.sberbank.bankapi.entities.PersonEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonDAO personDAO;
    private final AccountDAO accountDAO;
    private final CardDAO cardDAO;

    public PersonServiceImpl(PersonDAO personDAO, AccountDAO accountDAO,
                             CardDAO cardDAO) {
        this.personDAO = personDAO;
        this.accountDAO = accountDAO;
        this.cardDAO = cardDAO;
    }

    @Override
    public PersonEntity getPersonById(int personId) {
        return personDAO.getPerson(personId);
    }

    @Override
    public List<AccountEntity> getAccountsByPersonId(int personId) {
        return personDAO.getPerson(personId).getAccount();
    }

    @Override
    public List<CardEntity> getCardsByPersonId(int personId) {
        List<CardEntity> cards = new ArrayList<>();
        List<AccountEntity> accountEntity = getAccountsByPersonId(personId);
        for (AccountEntity account : accountEntity) {
            cards.add(cardDAO.getCardByAccountId(account.getId()));
        }
        return cards;
    }

    @Override
    public AccountEntity getAccountByAccountNumber(String accountNumber) {
        return accountDAO.getAccountByAccountNumber(accountNumber);
    }

    @Override
    public CardEntity getCardByCardNumber(String cardNumber) {
        return cardDAO.getCardByCardNumber(cardNumber);
    }

    @Override
    public List<PersonEntity> getAllPersons() {
        return personDAO.getAllPersons();
    }

    @Override
    public double getBalanceForCard(String cardNumber) {
        CardEntity cardEntity = getCardByCardNumber(cardNumber);
        return cardEntity.getAccountEntity().getBalance();
    }

    @Override
    public void createNewAccount(int personId, AccountEntity accountEntity) {
        PersonEntity personEntity = getPersonById(personId);
        personEntity.createAccount(accountEntity);
        accountDAO.saveAccount(accountEntity);
    }

    @Override
    public void createNewCard(AccountEntity accountEntity, CardEntity cardEntity) {
        accountEntity.createCard(cardEntity);
        cardDAO.saveCard(cardEntity);
    }

    @Override
    public void createPerson(PersonEntity personEntity) {
        personDAO.savePerson(personEntity);
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

    @Override
    public List<AccountEntity> transferMoney(String accountNumberFrom, String accountNumberTo, double sum) {
        AccountEntity accountFrom = getAccountByAccountNumber(accountNumberFrom);
        AccountEntity accountTo = getAccountByAccountNumber(accountNumberTo);
        List<AccountEntity> accountEntities = new ArrayList<>();
        if (accountFrom.getBalance() >= sum) {
            accountFrom.setBalance(accountFrom.getBalance() - sum);
            accountTo.setBalance(accountTo.getBalance() + sum);
            accountDAO.saveAccount(accountFrom);
            accountDAO.saveAccount(accountTo);
            accountEntities.add(accountFrom);
            accountEntities.add(accountTo);
        }
        return accountEntities;
    }
}
