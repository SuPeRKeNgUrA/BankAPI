package com.sberbank.bankapi.controller;

import com.sberbank.bankapi.DTO.AccountDTO;
import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.services.interfaces.AccountService;
import com.sberbank.bankapi.services.interfaces.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api")
public class AccountController {

    private final PersonService personService;
    private final AccountService accountService;

    @GetMapping("/clients/getAccounts/{personId}")
    public ResponseEntity<List<AccountDTO>> getAccountsByPersonId(@PathVariable("personId") int personId) {
        if (personService.getPersonById(personId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<AccountDTO> accounts = accountService.getAccountsByPersonId(personId);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/clients/getAccountByAccountNumber/{accountNumber}")
    public ResponseEntity<AccountDTO> getAccountByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
        AccountDTO accountDTO = accountService.getAccountByAccountNumber(accountNumber);
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/manager/createAccountForPerson/{personId}")
    public ResponseEntity<String> createAccount(@PathVariable("personId") int personId,
                                                @RequestBody AccountEntity accountEntity) {
        boolean isCreated = accountService.createNewAccount(personId, accountEntity);
        if (isCreated) {
            return new ResponseEntity<>("Счет открыт", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Нет заявок на открытие счета или заявка еще не подтверждена", HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/clients/transferMoney/{accountFrom}/{accountTo}/{sum}")
    public ResponseEntity<List<AccountDTO>> transferMoney(@PathVariable("accountFrom") String accountFrom,
                                                          @PathVariable("accountTo") String accountTo,
                                                          @PathVariable("sum") double sum) {
        try {
            accountService.getAccountByAccountNumber(accountFrom);
            accountService.getAccountByAccountNumber(accountTo);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<AccountDTO> refreshedAccounts = accountService.transferMoney(accountFrom, accountTo, sum);
        if (refreshedAccounts.size() == 0) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(refreshedAccounts, HttpStatus.OK);
    }

    @PostMapping(value = "clients/requestToCreateAccount/{accountNumber}")
    public ResponseEntity<String> requestToCreateCard(@PathVariable("accountNumber") String accountNumber) {
        boolean response = accountService.createRequestToCreateCard(accountNumber);
        if (response) {
            return new ResponseEntity<>("Заявка на выпуск карты подана", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("К данному аккаунту уже привязана карта или заявка на открытие карты уже подана", HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/manager/checkCardsRequests")
    public ResponseEntity<List<AccountDTO>> checkCardsRequests() {
        List<AccountDTO> accountsWithRequests = accountService.checkCardsRequests();
        return new ResponseEntity<>(accountsWithRequests, HttpStatus.OK);
    }

    @PostMapping(value = "/manager/confirmRequestToCreateCard/{accountNumber}")
    public ResponseEntity<String> confirmRequestToCreateCard(@PathVariable("accountNumber") String accountNumber) {
        accountService.confirmRequestToCreateCard(accountNumber);
        return new ResponseEntity("Заявка на выпуск карты подтверждена", HttpStatus.ACCEPTED);
    }

}
