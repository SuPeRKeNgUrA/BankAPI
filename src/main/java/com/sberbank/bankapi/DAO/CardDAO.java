package com.sberbank.bankapi.DAO;

import com.sberbank.bankapi.entities.CardEntity;
import com.sberbank.bankapi.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardDAO {

    private final HibernateUtil hibernateUtil;

    public CardDAO(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    public CardEntity getCardByAccountId(int accountId) {
        Session session = hibernateUtil.getSessionFactory().openSession();
        CardEntity cardEntity = session.get(CardEntity.class, accountId);
        return cardEntity;
    }

    public CardEntity getCardByCardNumber(String cardNumber) {
        Session session = hibernateUtil.getSessionFactory().openSession();
        Query<CardEntity> query = session.createQuery("from CardEntity where number = :requestNumber", CardEntity.class);
        query.setParameter("requestNumber", cardNumber);
        List<CardEntity> card = query.getResultList();
        return card.get(0);
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
