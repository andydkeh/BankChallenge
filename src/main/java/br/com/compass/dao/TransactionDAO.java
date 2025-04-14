package br.com.compass.dao;

import br.com.compass.models.Account;
import br.com.compass.models.Transaction;
import br.com.compass.models.Transfers;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TransactionDAO extends BaseDAO<Transaction>{
    public TransactionDAO() {
        super(Transaction.class);
    }

    public Transaction findByAccountID(Long accountId){
        try {
            TypedQuery<Transaction> query = em.createQuery(
                    "SELECT u FROM Transaction u WHERE u.accountId = :accountId",
                    Transaction.class
            );
            query.setParameter("accountId", accountId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Transaction> findByAllTransactionID(Long transactionId) {
        try {
            TypedQuery<Transaction> query = em.createQuery(
                    "SELECT u FROM Transaction u WHERE u.transfersId = :transactionId",
                    Transaction.class
            );
            query.setParameter("transactionId", transactionId);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}