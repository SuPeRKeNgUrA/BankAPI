package com.sberbank.bankapi.controller;

import com.sberbank.bankapi.DTO.AccountDTO;
import com.sberbank.bankapi.DTO.CardDTO;
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

@RestController
@AllArgsConstructor
@RequestMapping("api/clients/cards")
public class CardController {

    private final PersonService personService;
    private final AccountService accountService;
    private final CardService cardService;

    @GetMapping("/getCards/{personId}")
    public ResponseEntity<List<CardDTO>> getCardsById(@PathVariable("personId") int personId) {
        if (personService.getPersonById(personId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<CardDTO> cards = cardService.getCardsByPersonId(personId);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @GetMapping("/getBalanceForCard/{cardNumber}")
    public ResponseEntity<Double> getBalanceForCard(@PathVariable("cardNumber") String cardNumber) {
        double balance = cardService.getBalanceForCard(cardNumber);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    @PostMapping(value = "/createCardForAccount/{accountNumber}")
    public ResponseEntity<CardDTO> createCardForAccount(@PathVariable("accountNumber") String accountNumber,
                                                           @RequestBody CardEntity cardEntity) {
        String cardNumber = cardEntity.getNumber();
        AccountEntity accountEntity = accountService.getAccountEntityByAccountNumber(accountNumber);
        if (accountEntity.getCard() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        cardEntity.setAccountEntity(accountEntity);
        cardService.createNewCard(accountEntity, cardEntity);
        CardDTO newCard = cardService.getCardByCardNumber(cardNumber);
        return new ResponseEntity<>(newCard, HttpStatus.OK);
    }

    @PostMapping(value = "/addMoneyToCard/{cardNumber}/{sum}")
    public ResponseEntity<AccountDTO> addMoneyToCard(@PathVariable("cardNumber") String cardNumber,
                                                     @PathVariable("sum") double sum) {
        AccountDTO accountDTO = cardService.addMoneyToCard(cardNumber, sum);
//        CardDTO cardDTO = cardService.getCardByCardNumber(cardNumber);
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteCard/{cardNumber}")
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
