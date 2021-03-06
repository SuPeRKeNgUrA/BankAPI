package com.sberbank.bankapi.controller;

import com.sberbank.bankapi.DTO.MessageDTO;
import com.sberbank.bankapi.DTO.PersonDTO;
import com.sberbank.bankapi.entities.PersonEntity;
import com.sberbank.bankapi.services.interfaces.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("api")
public class PersonController {

    private final PersonService personService;

    @GetMapping("/manager/getPerson/{personId}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable("personId") int personId) {
        PersonDTO personDTO = personService.getPersonById(personId);
        if (personDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @GetMapping("/manager/getAllPersons")
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        List<PersonDTO> persons = personService.getAllPersons();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @PostMapping(value = "/manager/createPerson")
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonEntity personEntity) {
        PersonDTO personDTO = personService.createPerson(personEntity);
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/clients/addRequestToCreateAccount/{passport}")
    public ResponseEntity<MessageDTO> requestToCreateAccount(@PathVariable("passport") String passport) {
        Map<Boolean, MessageDTO> isCreated = personService.createRequestToCreateAccount(passport);
        if (isCreated.containsKey(true)) {
            return new ResponseEntity<>(isCreated.get(true), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(isCreated.get(false), HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/manager/checkAccountsRequests")
    public ResponseEntity<List<PersonDTO>> checkAccountsRequests() {
        List<PersonDTO> personsWithRequests = personService.checkAccountsRequests();
        return new ResponseEntity<>(personsWithRequests, HttpStatus.OK);
    }

    @PostMapping(value = "/manager/confirmRequestToCreateAccount/{passport}")
    public ResponseEntity<MessageDTO> confirmRequestToCreateAccount(@PathVariable("passport") String passport) {
        MessageDTO message = personService.confirmRequestToCreateAccount(passport);
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

}
