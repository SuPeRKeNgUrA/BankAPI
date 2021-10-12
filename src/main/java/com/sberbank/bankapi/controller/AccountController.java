package com.sberbank.bankapi.controller;

import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.services.interfaces.AccountService;
import com.sberbank.bankapi.services.interfaces.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/account")
@AllArgsConstructor
public class AccountController {

    private PersonService personService;
    private AccountService accountService;

    @GetMapping("/getAccounts/{personId}")
    public ResponseEntity<List<AccountEntity>> getAccountsByPersonId(@PathVariable("personId") int personId) {
        if (personService.getPersonById(personId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<AccountEntity> accountEntity = accountService.getAccountsByPersonId(personId);
        return new ResponseEntity<>(accountEntity, HttpStatus.OK);
    }

    @GetMapping("/getAccountByAccountNumber/{accountNumber}")
    public ResponseEntity<AccountEntity> getAccountByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
        AccountEntity accountEntity = accountService.getAccountByAccountNumber(accountNumber);
        return new ResponseEntity<>(accountEntity, HttpStatus.OK);
    }

    @PostMapping(value = "/createAccountForPerson/{personId}")
    public ResponseEntity<AccountEntity> createAccount(@PathVariable("personId") int personId,
                                                       @RequestBody AccountEntity accountEntity) {
        accountService.createNewAccount(personId, accountEntity);
        return new ResponseEntity<>(accountEntity, HttpStatus.OK);
    }

    @PostMapping(value = "/transferMoney/{accountFrom}/{accountTo}/{sum}")
    public ResponseEntity<List<AccountEntity>> transferMoney(@PathVariable("accountFrom") String accountFrom,
                                                             @PathVariable("accountTo") String accountTo,
                                                             @PathVariable("sum") double sum) {
        List<AccountEntity> refreshedAccounts = accountService.transferMoney(accountFrom, accountTo, sum);
        if (refreshedAccounts.size() == 0) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(refreshedAccounts, HttpStatus.OK);
    }

}
