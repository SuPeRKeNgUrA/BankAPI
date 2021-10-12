package com.sberbank.bankapi.controller;

import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.entities.CardEntity;
import com.sberbank.bankapi.entities.PersonEntity;
import com.sberbank.bankapi.services.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/getPerson/{personId}")
    public ResponseEntity<PersonEntity> getPersonById(@PathVariable("personId") int personId) {
        PersonEntity personEntity = personService.getPersonById(personId);
        if (personEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(personEntity, HttpStatus.OK);
    }

    @GetMapping("/getAccounts/{personId}")
    public ResponseEntity<List<AccountEntity>> getAccountsByPersonId(@PathVariable("personId") int personId) {
        if (personService.getPersonById(personId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<AccountEntity> accountEntity = personService.getAccountsByPersonId(personId);
        return new ResponseEntity<>(accountEntity, HttpStatus.OK);
    }

    @GetMapping("/getCards/{personId}")
    public ResponseEntity<List<CardEntity>> getCardsById(@PathVariable("personId") int personId) {
        if (personService.getPersonById(personId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<CardEntity> cardEntity = personService.getCardsByPersonId(personId);
        return new ResponseEntity<>(cardEntity, HttpStatus.OK);
    }

    @GetMapping("/getAllPersons")
    public ResponseEntity<List<PersonEntity>> getAllPersons() {
        List<PersonEntity> persons = personService.getAllPersons();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/getAccountByAccountNumber/{accountNumber}")
    public ResponseEntity<AccountEntity> getAccountByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
        AccountEntity accountEntity = personService.getAccountByAccountNumber(accountNumber);
        return new ResponseEntity<>(accountEntity, HttpStatus.OK);
    }

    @GetMapping("/getBalanceForCard/{cardNumber}")
    public ResponseEntity<Double> getBalanceForCard(@PathVariable("cardNumber") String cardNumber) {
        double balance = personService.getBalanceForCard(cardNumber);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    @PostMapping(value = "/createPerson")
    public ResponseEntity<PersonEntity> createPerson(@RequestBody PersonEntity personEntity) {
        personService.createPerson(personEntity);
        return new ResponseEntity<>(personEntity, HttpStatus.OK);
    }

    @PostMapping(value = "/createAccountForPerson/{personId}")
    public ResponseEntity<AccountEntity> createAccount(@PathVariable("personId") int personId,
                                                       @RequestBody AccountEntity accountEntity) {
        personService.createNewAccount(personId, accountEntity);
        return new ResponseEntity<>(accountEntity, HttpStatus.OK);
    }

    @PostMapping(value = "/createCardForAccount/{accountNumber}")
    public ResponseEntity<CardEntity> createCardForAccount(@PathVariable("accountNumber") String accountNumber,
                                                           @RequestBody CardEntity cardEntity) {
        String cardNumber = cardEntity.getNumber();
        AccountEntity accountEntity = personService.getAccountByAccountNumber(accountNumber);
        if (accountEntity.getCard() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        cardEntity.setAccountEntity(accountEntity);
        personService.createNewCard(accountEntity, cardEntity);
        CardEntity newCard = personService.getCardByCardNumber(cardNumber);
        return new ResponseEntity<>(newCard, HttpStatus.OK);
    }

    @PostMapping(value = "/addMoneyToCard/{cardNumber}/{sum}")
    public ResponseEntity<AccountEntity> addMoneyToCard(@PathVariable("cardNumber") String cardNumber,
                                                        @PathVariable("sum") double sum) {
        personService.addMoneyToCard(cardNumber, sum);
        CardEntity cardEntity = personService.getCardByCardNumber(cardNumber);
        return new ResponseEntity<>(cardEntity.getAccountEntity(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteCard/{cardNumber}")
    public ResponseEntity<AccountEntity> deleteCard(@PathVariable("cardNumber") String cardNumber) {
        CardEntity cardEntity;
        try {
            cardEntity = personService.getCardByCardNumber(cardNumber);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String account = cardEntity.getAccountEntity().getAccount();
        personService.deleteCard(cardNumber);
        AccountEntity accountEntity = personService.getAccountByAccountNumber(account);
        return new ResponseEntity<>(accountEntity, HttpStatus.OK);
    }

    @PostMapping(value = "/transferMoney/{accountFrom}/{accountTo}/{sum}")
    public ResponseEntity<List<AccountEntity>> transferMoney(@PathVariable("accountFrom") String accountFrom,
                                                             @PathVariable("accountTo") String accountTo,
                                                             @PathVariable("sum") double sum) {
        List<AccountEntity> refreshedAccounts = personService.transferMoney(accountFrom, accountTo, sum);
        if (refreshedAccounts.size() == 0) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(refreshedAccounts, HttpStatus.OK);
    }
}


