package com.sberbank.bankapi.controller;

import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.entities.CardEntity;
import com.sberbank.bankapi.services.implementations.AccountServiceImpl;
import com.sberbank.bankapi.services.interfaces.AccountService;
import com.sberbank.bankapi.services.interfaces.CardService;
import com.sberbank.bankapi.services.interfaces.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/card")
@AllArgsConstructor
public class CardController {

    private PersonService personService;
    private AccountService accountService;
    private CardService cardService;
    private AccountServiceImpl accountServiceImpl;

    @GetMapping("/getCards/{personId}")
    public ResponseEntity<List<CardEntity>> getCardsById(@PathVariable("personId") int personId) {
        if (personService.getPersonById(personId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<CardEntity> cardEntity = cardService.getCardsByPersonId(personId);
        return new ResponseEntity<>(cardEntity, HttpStatus.OK);
    }

    @GetMapping("/getBalanceForCard/{cardNumber}")
    public ResponseEntity<Double> getBalanceForCard(@PathVariable("cardNumber") String cardNumber) {
        double balance = cardService.getBalanceForCard(cardNumber);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    @PostMapping(value = "/createCardForAccount/{accountNumber}")
    public ResponseEntity<CardEntity> createCardForAccount(@PathVariable("accountNumber") String accountNumber,
                                                           @RequestBody CardEntity cardEntity) {
        String cardNumber = cardEntity.getNumber();
        AccountEntity accountEntity = accountService.getAccountByAccountNumber(accountNumber);
        if (accountEntity.getCard() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        cardEntity.setAccountEntity(accountEntity);
        cardService.createNewCard(accountEntity, cardEntity);
        CardEntity newCard = cardService.getCardByCardNumber(cardNumber);
        return new ResponseEntity<>(newCard, HttpStatus.OK);
    }

    @PostMapping(value = "/addMoneyToCard/{cardNumber}/{sum}")
    public ResponseEntity<AccountEntity> addMoneyToCard(@PathVariable("cardNumber") String cardNumber,
                                                        @PathVariable("sum") double sum) {
        cardService.addMoneyToCard(cardNumber, sum);
        CardEntity cardEntity = cardService.getCardByCardNumber(cardNumber);
        return new ResponseEntity<>(cardEntity.getAccountEntity(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteCard/{cardNumber}")
    public ResponseEntity<AccountEntity> deleteCard(@PathVariable("cardNumber") String cardNumber) {
        CardEntity cardEntity;
        try {
            cardEntity = cardService.getCardByCardNumber(cardNumber);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String account = cardEntity.getAccountEntity().getAccount();
        cardService.deleteCard(cardNumber);
        AccountEntity accountEntity = accountService.getAccountByAccountNumber(account);
        return new ResponseEntity<>(accountEntity, HttpStatus.OK);
    }

}
