package br.com.andydkeh.dao;

import br.com.andydkeh.enums.TransactionsType;
import br.com.andydkeh.models.Transaction;
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

    public List<Transaction> findByAllTransactionID(Long accountId) {
        try {
            TypedQuery<Transaction> query = em.createQuery(
                    "SELECT u FROM Transaction u WHERE u.accountId = :accountId",
                    Transaction.class
            );
            query.setParameter("accountId", accountId);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Transaction> findByAllTransactionIdChargeback(Long accountId) {
        List<String> types = List.of(TransactionsType.DEPOSITS.name(), TransactionsType.TRANSFERS.name());
        try {
            TypedQuery<Transaction> query = em.createQuery(
                    "SELECT u FROM Transaction u WHERE u.accountId = :accountId AND u.transactionType in :types",
                    Transaction.class
            );
            query.setParameter("accountId", accountId).setParameter("types", types);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}