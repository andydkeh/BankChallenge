package br.com.compass.dao;

import br.com.compass.models.Account;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class AccountDAO extends BaseDAO<Account>{
    public AccountDAO() {
        super(Account.class);
    }

    public Account findByUserID(Long userId){
        try {
            TypedQuery<Account> query = em.createQuery(
                    "SELECT u FROM Account u WHERE u.userId = :userId",
                    Account.class
            );
            query.setParameter("userId", userId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Account> findByUserIDAllAccounts(Long userId){
        try {
            TypedQuery<Account> query = em.createQuery(
                    "SELECT u FROM Account u WHERE u.userId = :userId",
                    Account.class
            );
            query.setParameter("userId", userId);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}