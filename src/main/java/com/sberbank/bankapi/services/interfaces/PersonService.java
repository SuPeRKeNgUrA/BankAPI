package com.sberbank.bankapi.services.interfaces;

import com.sberbank.bankapi.entities.PersonEntity;

import java.util.List;

public interface PersonService {

    PersonEntity getPersonById(int personId);

    PersonEntity getPersonEntityById(int personId);

    void createPerson(PersonEntity personEntity);

    List<PersonEntity> getAllPersons();

}
