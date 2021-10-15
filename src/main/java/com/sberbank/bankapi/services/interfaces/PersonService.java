package com.sberbank.bankapi.services.interfaces;

import com.sberbank.bankapi.DTO.MessageDTO;
import com.sberbank.bankapi.DTO.PersonDTO;
import com.sberbank.bankapi.entities.PersonEntity;

import java.util.List;
import java.util.Map;

/**
 * Сервис для работы с данными о клиентах, запросах об открытии счета и подтверждения этой операции со стороны банка
 */
public interface PersonService {

    PersonDTO getPersonById(int personId);

    PersonEntity getPersonEntityById(int personId);

    PersonDTO createPerson(PersonEntity personEntity);

    List<PersonDTO> getAllPersons();

    Map<Boolean, MessageDTO> createRequestToCreateAccount(String passport);

    List<PersonDTO> checkAccountsRequests();

    MessageDTO confirmRequestToCreateAccount(String passport);

    PersonDTO transferToPersonDTO(PersonEntity personEntity);

}
