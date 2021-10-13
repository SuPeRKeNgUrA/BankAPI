package com.sberbank.bankapi.controller;

import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.entities.CardEntity;
import com.sberbank.bankapi.entities.PersonEntity;
import com.sberbank.bankapi.services.interfaces.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("api/clients")
public class PersonController {

    private final PersonService personService;

    @PostMapping(value = "requestToCreateAccount/{passport}")
    public ResponseEntity<String> requestToCreateAccount(@PathVariable("passport") String passport) {
        personService.createRequestToCreateAccount(passport);
        return new ResponseEntity<>("Заявка подана", HttpStatus.ACCEPTED);
    }

}
