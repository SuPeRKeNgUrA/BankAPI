package com.sberbank.bankapi.services.implementations;

import com.sberbank.bankapi.DAO.PersonDAO;
import com.sberbank.bankapi.DTO.PersonDTO;
import com.sberbank.bankapi.entities.PersonEntity;
import com.sberbank.bankapi.services.interfaces.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonDAO personDAO;

    @Override
    public PersonDTO getPersonById(int personId) {
        return transferToPersonDTO(personDAO.getPerson(personId));
    }

    @Override
    public PersonEntity getPersonEntityById(int personId) {
        return personDAO.getPerson(personId);
    }

    @Override
    public List<PersonDTO> getAllPersons() {
        List<PersonDTO> persons = new ArrayList<>();
        for (PersonEntity person : personDAO.getAllPersons()) {
            persons.add(transferToPersonDTO(person));
        }
        return persons;
    }

    @Override
    public PersonDTO createPerson(PersonEntity personEntity) {
        personDAO.savePerson(personEntity);
        return getPersonById(personEntity.getId());
    }

    @Override
    public boolean createRequestToCreateAccount(String passport) {
        PersonEntity personEntity = personDAO.getPersonByPassport(passport);
        if (personEntity.getRequestAccount() == 0) {
            personEntity.setRequestAccount(personEntity.getRequestAccount() + 1);
            personDAO.savePerson(personEntity);
            return true;
        }
        return false;
    }

    @Override
    public List<PersonDTO> checkAccountsRequests() {
        List<PersonEntity> personEntities = personDAO.getAllPersons();
        List<PersonDTO> personsWithRequests = new ArrayList<>();
        for (PersonEntity person : personEntities) {
            if (person.getRequestAccount() != 0) {
                personsWithRequests.add(transferToPersonDTO(person));
            }
        }
        return personsWithRequests;
    }

    @Override
    public void confirmRequestToCreateAccount(String passport) {
        PersonEntity personEntity = personDAO.getPersonByPassport(passport);
        if (personEntity.getRequestAccount() > personEntity.getConfirmedRequest()) {
            personEntity.setConfirmedRequest(personEntity.getRequestAccount());
        }
        personDAO.savePerson(personEntity);
    }

    @Override
    public PersonDTO transferToPersonDTO(PersonEntity personEntity) {
        return PersonDTO.builder()
                .id(personEntity.getId())
                .name(personEntity.getName())
                .surname(personEntity.getSurname())
                .phone(personEntity.getPhone())
                .passport(personEntity.getPassport())
                .account(personEntity.getAccount())
                .requestAccount(personEntity.getRequestAccount())
                .confirmedRequest(personEntity.getConfirmedRequest())
                .build();
    }
}
