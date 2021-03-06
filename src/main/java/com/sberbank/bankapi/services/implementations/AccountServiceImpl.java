package com.sberbank.bankapi.services.implementations;

import com.sberbank.bankapi.DAO.AccountDAO;
import com.sberbank.bankapi.DAO.PersonDAO;
import com.sberbank.bankapi.DTO.AccountDTO;
import com.sberbank.bankapi.DTO.MessageDTO;
import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.entities.PersonEntity;
import com.sberbank.bankapi.services.interfaces.AccountService;
import com.sberbank.bankapi.services.interfaces.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final PersonDAO personDAO;
    private final PersonService personService;
    private final AccountDAO accountDAO;

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
    public Map<Boolean, MessageDTO> createNewAccount(int personId, AccountEntity accountEntity) {
        PersonEntity personEntity = personService.getPersonEntityById(personId);
        Map<Boolean, MessageDTO> message = new HashMap<>();
        if ((personEntity.getRequestAccount() == personEntity.getConfirmedRequest()) &&
                personEntity.getRequestAccount() != 0) {
            personEntity.createAccount(accountEntity);
            personEntity.setRequestAccount(0);
            personEntity.setConfirmedRequest(0);
            accountDAO.saveAccount(accountEntity);
            personDAO.savePerson(personEntity);
            message.put(true, MessageDTO.builder().message("???????? ????????????").build());
        } else {
            message.put(false, MessageDTO.builder().message("?????? ???????????? ???? ???????????????? ?????????? ?????? ???????????? ?????? ???? ????????????????????????").build());
        }
        return message;
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
    public Map<Boolean, MessageDTO> createRequestToCreateCard(String accountNumber) {
        AccountEntity accountEntity = accountDAO.getAccountByAccountNumber(accountNumber);
        Map<Boolean, MessageDTO> message = new HashMap<>();
        if (accountEntity.getRequestCard() == 0) {
            accountEntity.setRequestCard(accountEntity.getRequestCard() + 1);
            accountDAO.saveAccount(accountEntity);
            message.put(true, MessageDTO.builder().message("???????????? ???? ???????????? ?????????? ????????????").build());
        } else {
            message.put(true, MessageDTO.builder().message("?? ?????????????? ???????????????? ?????? ?????????????????? ?????????? ?????? ???????????? ???? ???????????????? ?????????? ?????? ????????????").build());
        }
        return message;
    }

    @Override
    public List<AccountDTO> checkCardsRequests() {
        List<AccountEntity> accountEntities = accountDAO.getAllAccounts();
        List<AccountDTO> accountsWithRequests = new ArrayList<>();
        for (AccountEntity account : accountEntities) {
            if (account.getRequestCard() != 0) {
                accountsWithRequests.add(transferToAccountDTO(account));
            }
        }
        return accountsWithRequests;
    }

    @Override
    public MessageDTO confirmRequestToCreateCard(String accountNumber) {
        AccountEntity accountEntity = accountDAO.getAccountByAccountNumber(accountNumber);
        if (accountEntity.getRequestCard() > accountEntity.getConfirmedRequest()) {
            accountEntity.setConfirmedRequest(accountEntity.getRequestCard());
        }
        accountDAO.saveAccount(accountEntity);
        return MessageDTO.builder().message("???????????? ???? ???????????? ?????????? ????????????????????????").build();
    }

    @Override
    public AccountDTO transferToAccountDTO(AccountEntity accountEntity) {
        return AccountDTO.builder()
                .id(accountEntity.getId())
                .account(accountEntity.getAccount())
                .personEntity(accountEntity.getPersonEntity())
                .balance(accountEntity.getBalance())
                .card(accountEntity.getCard())
                .requestCard(accountEntity.getRequestCard())
                .confirmedRequest(accountEntity.getConfirmedRequest())
                .build();
    }

}
