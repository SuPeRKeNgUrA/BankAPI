package com.sberbank.bankapi.DAO;

import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.entities.PersonEntity;
import com.sberbank.bankapi.util.HibernateUtil;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class PersonDAO {

    private final HibernateUtil hibernateUtil;

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

    public PersonEntity getPersonByPassport(String passport) {
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            Query<PersonEntity> query = session.createQuery("from PersonEntity where passport = :requestNumber", PersonEntity.class);
            query.setParameter("requestNumber", passport);
            PersonEntity personEntity = query.getSingleResult();
            return personEntity;
        }
    }

    public void savePerson(PersonEntity personEntity) {
        Transaction tx = null;
        try (Session session = hibernateUtil.getSessionFactory().openSession();) {
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

    public void updatePerson(PersonEntity personEntity) {
        Transaction tx = null;
        try (Session session = hibernateUtil.getSessionFactory().openSession();) {
            tx = session.beginTransaction();
            session.update(personEntity);
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
    }

}
