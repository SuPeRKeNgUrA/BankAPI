package com.sberbank.bankapi.services.interfaces;

import com.sberbank.bankapi.DTO.AccountDTO;
import com.sberbank.bankapi.DTO.MessageDTO;
import com.sberbank.bankapi.entities.AccountEntity;

import java.util.List;
import java.util.Map;

/**
 * Сервис для получения данных о счетах клиентов банка, запросах о выпуске карты и подтверждения этой операции со стороны банка
 */
public interface AccountService {

    List<AccountDTO> getAccountsByPersonId(int personId);

    AccountDTO getAccountByAccountNumber(String accountNumber);

    AccountEntity getAccountEntityByAccountNumber(String accountNumber);

    Map<Boolean, MessageDTO> createNewAccount(int personId, AccountEntity accountEntity);

    List<AccountDTO> transferMoney(String accountNumberFrom, String accountNumberTo, double sum);

    Map<Boolean, MessageDTO> createRequestToCreateCard(String accountNumber);

    List<AccountDTO> checkCardsRequests();

    MessageDTO confirmRequestToCreateCard(String accountNumber);

    AccountDTO transferToAccountDTO(AccountEntity accountEntity);

}
