package com.sberbank.bankapi.DAO;

import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDAO {

    private final HibernateUtil hibernateUtil;

    public AccountDAO(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    public AccountEntity getAccount(int personId) {
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            AccountEntity accountEntity = session.get(AccountEntity.class, personId);
            return accountEntity;
        }
    }

    public AccountEntity getAccountByAccountNumber(String accountNumber) {
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            Query<AccountEntity> query = session.createQuery("from AccountEntity where account = :requestNumber", AccountEntity.class);
            query.setParameter("requestNumber", accountNumber);
            AccountEntity account = query.getSingleResult();
            return account;
        }
    }

    public void saveAccount(AccountEntity accountEntity) {
        Transaction tx = null;
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(accountEntity);
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
    }

}
