package com.sberbank.bankapi.services.implementations;

import com.sberbank.bankapi.DAO.PersonDAO;
import com.sberbank.bankapi.entities.PersonEntity;
import com.sberbank.bankapi.services.interfaces.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonDAO personDAO;

    @Override
    public PersonEntity getPersonById(int personId) {
        return personDAO.getPerson(personId);
    }

    @Override
    public PersonEntity getPersonEntityById(int personId) {
        return personDAO.getPerson(personId);
    }

    @Override
    public List<PersonEntity> getAllPersons() {
        return personDAO.getAllPersons();
    }

    @Override
    public void createPerson(PersonEntity personEntity) {
        personDAO.savePerson(personEntity);
    }

}
