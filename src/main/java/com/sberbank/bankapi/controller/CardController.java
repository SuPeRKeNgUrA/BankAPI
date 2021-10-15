package com.sberbank.bankapi.controller;

import com.sberbank.bankapi.DTO.AccountDTO;
import com.sberbank.bankapi.DTO.CardDTO;
import com.sberbank.bankapi.DTO.MessageDTO;
import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.entities.CardEntity;
import com.sberbank.bankapi.services.interfaces.AccountService;
import com.sberbank.bankapi.services.interfaces.CardService;
import com.sberbank.bankapi.services.interfaces.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("api")
public class CardController {

    private final PersonService personService;
    private final AccountService accountService;
    private final CardService cardService;

    @GetMapping("/clients/getCards/{personId}")
    public ResponseEntity<List<CardDTO>> getCardsById(@PathVariable("personId") int personId) {
        if (personService.getPersonById(personId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<CardDTO> cards = cardService.getCardsByPersonId(personId);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @GetMapping("/clients/getBalanceForCard/{cardNumber}")
    public ResponseEntity<Double> getBalanceForCard(@PathVariable("cardNumber") String cardNumber) {
        double balance = cardService.getBalanceForCard(cardNumber);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    @PostMapping(value = "/manager/createCardForAccount/{accountNumber}")
    public ResponseEntity<MessageDTO> createCardForAccount(@PathVariable("accountNumber") String accountNumber,
                                                           @RequestBody CardEntity cardEntity) {
        AccountEntity accountEntity = accountService.getAccountEntityByAccountNumber(accountNumber);
        if (accountEntity.getCard() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        cardEntity.setAccountEntity(accountEntity);
        Map<Boolean, MessageDTO> isCreated = cardService.createNewCard(accountEntity, cardEntity);
        if (isCreated.containsKey(true)) {
            return new ResponseEntity<>(isCreated.get(true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(isCreated.get(false), HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/clients/addMoneyToCard/{cardNumber}/{sum}")
    public ResponseEntity<AccountDTO> addMoneyToCard(@PathVariable("cardNumber") String cardNumber,
                                                     @PathVariable("sum") double sum) {
        AccountDTO accountDTO = cardService.addMoneyToCard(cardNumber, sum);
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/manager/deleteCard/{cardNumber}")
    public ResponseEntity<AccountDTO> deleteCard(@PathVariable("cardNumber") String cardNumber) {
        try {
            cardService.getCardEntityByCardNumber(cardNumber);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AccountDTO accountDTO = cardService.deleteCard(cardNumber);
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

}
