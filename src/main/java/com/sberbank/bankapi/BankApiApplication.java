package com.sberbank.bankapi;

import com.sberbank.bankapi.DAO.PersonDAO;
import com.sberbank.bankapi.services.interfaces.PersonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankApiApplication implements CommandLineRunner {

    private final PersonService personService;
    private final PersonDAO personDAO;

    public BankApiApplication(PersonService personService, PersonDAO personDAO) {
        this.personService = personService;
        this.personDAO = personDAO;
    }

    public static void main(String[] args) {
        SpringApplication.run(BankApiApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
//        System.out.println(personDAO.getPerson(2));
//        System.out.println(personService.getNameByPersonId(2));
//        System.out.println(personService.getAccountsByPersonId(2));
    }
}
