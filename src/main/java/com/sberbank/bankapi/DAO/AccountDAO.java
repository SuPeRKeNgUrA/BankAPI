package com.sberbank.bankapi.DAO;

import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountDAO {

    private final HibernateUtil hibernateUtil;

    public AccountDAO(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    public AccountEntity getAccount(int personId) {
        Session session = hibernateUtil.getSessionFactory().openSession();
        AccountEntity accountEntity = session.get(AccountEntity.class, personId);
        return accountEntity;
    }

    public AccountEntity getAccountByAccountNumber(String accountNumber) {
        Session session = hibernateUtil.getSessionFactory().openSession();
        Query<AccountEntity> query = session.createQuery("from AccountEntity where account = :requestNumber", AccountEntity.class);
        query.setParameter("requestNumber", accountNumber);
        List<AccountEntity> account = query.getResultList();
        return account.get(0);
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
