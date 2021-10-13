package com.sberbank.bankapi.services.implementations;

import com.sberbank.bankapi.DAO.AccountDAO;
import com.sberbank.bankapi.DAO.PersonDAO;
import com.sberbank.bankapi.DTO.AccountDTO;
import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.entities.PersonEntity;
import com.sberbank.bankapi.services.interfaces.AccountService;
import com.sberbank.bankapi.services.interfaces.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final PersonDAO personDAO;
    private final PersonService personService;
    private final AccountDAO accountDAO;

//    @Override
//    public List<AccountEntity> getAccountsByPersonId(int personId) {
//        return personDAO.getPerson(personId).getAccount();
//    }
//
//    @Override
//    public AccountEntity getAccountByAccountNumber(String accountNumber) {
//        return accountDAO.getAccountByAccountNumber(accountNumber);
//    }
//
//    @Override
//    public void createNewAccount(int personId, AccountEntity accountEntity) {
//        PersonEntity personEntity = personService.getPersonById(personId);
//        personEntity.createAccount(accountEntity);
//        accountDAO.saveAccount(accountEntity);
//    }
//
//    @Override
//    public List<AccountEntity> transferMoney(String accountNumberFrom, String accountNumberTo, double sum) {
//        AccountEntity accountFrom = getAccountByAccountNumber(accountNumberFrom);
//        AccountEntity accountTo = getAccountByAccountNumber(accountNumberTo);
//        List<AccountEntity> accountEntities = new ArrayList<>();
//        if (accountFrom.getBalance() >= sum) {
//            accountFrom.setBalance(accountFrom.getBalance() - sum);
//            accountTo.setBalance(accountTo.getBalance() + sum);
//            accountDAO.saveAccount(accountFrom);
//            accountDAO.saveAccount(accountTo);
//            accountEntities.add(accountFrom);
//            accountEntities.add(accountTo);
//        }
//        return accountEntities;
//    }

    @Override
    public List<AccountDTO> getAccountsByPersonId(int personId) {
        List<AccountDTO> accounts = new ArrayList<>();
        for (AccountEntity account : personDAO.getPerson(personId).getAccount()) {
            accounts.add(transferToAccountDTO(account));
        }
        return accounts;
    }

    @Override
    public AccountDTO getAccountByAccountNumber(String accountNumber) {
        return transferToAccountDTO(accountDAO.getAccountByAccountNumber(accountNumber));
    }

    @Override
    public AccountEntity getAccountEntityByAccountNumber(String accountNumber) {
        return accountDAO.getAccountByAccountNumber(accountNumber);
    }

    @Override
    public AccountDTO createNewAccount(int personId, AccountEntity accountEntity) {
        PersonEntity personEntity = personService.getPersonEntityById(personId);
        personEntity.createAccount(accountEntity);
        accountDAO.saveAccount(accountEntity);
        return transferToAccountDTO(accountEntity);
    }

    @Override
    public List<AccountDTO> transferMoney(String accountNumberFrom, String accountNumberTo, double sum) {
        AccountEntity accountFrom = accountDAO.getAccountByAccountNumber(accountNumberFrom);
        AccountEntity accountTo = accountDAO.getAccountByAccountNumber(accountNumberTo);
        List<AccountDTO> accounts = new ArrayList<>();
        if (accountFrom.getBalance() >= sum) {
            accountFrom.setBalance(accountFrom.getBalance() - sum);
            accountTo.setBalance(accountTo.getBalance() + sum);
            accountDAO.saveAccount(accountFrom);
            accountDAO.saveAccount(accountTo);
            accounts.add(transferToAccountDTO(accountFrom));
            accounts.add(transferToAccountDTO(accountTo));
        }
        return accounts;
    }

    @Override
    public AccountDTO transferToAccountDTO(AccountEntity accountEntity) {
        return AccountDTO.builder()
                .id(accountEntity.getId())
                .account(accountEntity.getAccount())
                .personEntity(accountEntity.getPersonEntity())
                .balance(accountEntity.getBalance())
                .card(accountEntity.getCard())
                .build();
    }
}
