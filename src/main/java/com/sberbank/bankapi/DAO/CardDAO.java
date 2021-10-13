package com.sberbank.bankapi.DAO;

import com.sberbank.bankapi.entities.AccountEntity;
import com.sberbank.bankapi.entities.CardEntity;
import com.sberbank.bankapi.util.HibernateUtil;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CardDAO {

    private final HibernateUtil hibernateUtil;
    private final AccountDAO accountDAO;

    public CardEntity getCardByAccountId(int accountId) {
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            CardEntity cardEntity = session.get(CardEntity.class, accountId);
            return cardEntity;
        }
    }

    public CardEntity getCardByAccountNumber(String accountNumber) {
            AccountEntity accountEntity = accountDAO.getAccountByAccountNumber(accountNumber);
            return accountEntity.getCard();
    }

    public CardEntity getCardByCardNumber(String cardNumber) {
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            Query<CardEntity> query = session.createQuery("from CardEntity where number = :requestNumber", CardEntity.class);
            query.setParameter("requestNumber", cardNumber);
            CardEntity card = query.getSingleResult();
            return card;
        }
    }

    public void saveCard(CardEntity cardEntity) {
        Transaction tx = null;
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(cardEntity);
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
    }

    public void deleteCard(String cardNumber) {
        CardEntity cardEntity = getCardByCardNumber(cardNumber);
        Transaction tx = null;
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(cardEntity);
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
    }

}
