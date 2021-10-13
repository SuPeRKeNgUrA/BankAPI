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
@RequestMapping("api/clients/accounts")
public class AccountController {

    private final PersonService personService;
    private final AccountService accountService;

    @GetMapping("/getAccounts/{personId}")
    public ResponseEntity<List<AccountDTO>> getAccountsByPersonId(@PathVariable("personId") int personId) {
        if (personService.getPersonById(personId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<AccountDTO> accounts = accountService.getAccountsByPersonId(personId);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/getAccountByAccountNumber/{accountNumber}")
    public ResponseEntity<AccountDTO> getAccountByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
        AccountDTO accountDTO = accountService.getAccountByAccountNumber(accountNumber);
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/createAccountForPerson/{personId}")
    public ResponseEntity<AccountDTO> createAccount(@PathVariable("personId") int personId,
                                                    @RequestBody AccountEntity accountEntity) {
        AccountDTO accountDTO = accountService.createNewAccount(personId, accountEntity);
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/transferMoney/{accountFrom}/{accountTo}/{sum}")
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

}
