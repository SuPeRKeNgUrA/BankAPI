package com.sberbank.bankapi.DAO;

import com.sberbank.bankapi.entities.PersonEntity;
import com.sberbank.bankapi.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonDAO {

    private final HibernateUtil hibernateUtil;

    public PersonDAO(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    public PersonEntity getPerson(int personId) {
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            PersonEntity personEntity = session.get(PersonEntity.class, personId);
            return personEntity;
        }
    }

    public List<PersonEntity> getAllPersons() {
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            Query<PersonEntity> query = session.createQuery("from PersonEntity", PersonEntity.class);
            List<PersonEntity> persons = query.getResultList();
            return persons;
        }
    }

    public void savePerson(PersonEntity personEntity) {
        Transaction tx = null;
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(personEntity);
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
    }

}
