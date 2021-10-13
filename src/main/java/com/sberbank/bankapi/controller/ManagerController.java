package com.sberbank.bankapi.controller;

import com.sberbank.bankapi.DTO.PersonDTO;
import com.sberbank.bankapi.entities.PersonEntity;
import com.sberbank.bankapi.services.interfaces.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/manager")
public class ManagerController {

    private final PersonService personService;

//    @GetMapping("/getPerson/{personId}")
//    public ResponseEntity<PersonEntity> getPersonById(@PathVariable("personId") int personId) {
//        PersonEntity personEntity = personService.getPersonById(personId);
//        if (personEntity == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(personEntity, HttpStatus.OK);
//    }
//
//    @GetMapping("/getAllPersons")
//    public ResponseEntity<List<PersonEntity>> getAllPersons() {
//        List<PersonEntity> persons = personService.getAllPersons();
//        return new ResponseEntity<>(persons, HttpStatus.OK);
//    }
//
//    @PostMapping(value = "/createPerson")
//    public ResponseEntity<PersonEntity> createPerson(@RequestBody PersonEntity personEntity) {
//        personService.createPerson(personEntity);
//        return new ResponseEntity<>(personEntity, HttpStatus.OK);
//    }

    @GetMapping("/getPerson/{personId}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable("personId") int personId) {
        PersonDTO personDTO = personService.getPersonById(personId);
        if (personDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @GetMapping("/getAllPersons")
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        List<PersonDTO> persons = personService.getAllPersons();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @PostMapping(value = "/createPerson")
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonEntity personEntity) {
        PersonDTO personDTO = personService.createPerson(personEntity);
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @PostMapping(value = "checkRequestsAndConfirm")
    public ResponseEntity<> checkRequestsAndConfirm() {

    }

}


