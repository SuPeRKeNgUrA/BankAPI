package com.sberbank.bankapi.services.interfaces;

import com.sberbank.bankapi.DTO.PersonDTO;
import com.sberbank.bankapi.entities.PersonEntity;

import java.util.List;

public interface PersonService {

//    PersonEntity getPersonById(int personId);
//
//    void createPerson(PersonEntity personEntity);
//
//    List<PersonEntity> getAllPersons();

    PersonDTO getPersonById(int personId);

    PersonEntity getPersonEntityById(int personId);

    PersonDTO createPerson(PersonEntity personEntity);

    List<PersonDTO> getAllPersons();

    PersonDTO transferToPersonDTO(PersonEntity personEntity);

    void createRequestToCreateAccount(String passport);

    void checkRequestsAndConfirm();

}
