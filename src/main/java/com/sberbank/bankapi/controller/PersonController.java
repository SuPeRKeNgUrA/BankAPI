package com.sberbank.bankapi.controller;

import com.sberbank.bankapi.entities.PersonEntity;
import com.sberbank.bankapi.services.interfaces.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/getPerson/{personId}")
    public ResponseEntity<PersonEntity> getPersonById(@PathVariable("personId") int personId) {
        PersonEntity personDto = personService.getPersonById(personId);
        if (personDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(personDto, HttpStatus.OK);
    }

    @GetMapping("/getAllPersons")
    public ResponseEntity<List<PersonEntity>> getAllPersons() {
        List<PersonEntity> persons = personService.getAllPersons();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @PostMapping(value = "/createPerson")
    public ResponseEntity<PersonEntity> createPerson(@RequestBody PersonEntity personEntity) {
        personService.createPerson(personEntity);
        return new ResponseEntity<>(personEntity, HttpStatus.OK);
    }

}


