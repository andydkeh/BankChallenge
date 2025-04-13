package br.com.compass.dao;

import br.com.compass.enums.TransactionsType;
import br.com.compass.models.Account;
import br.com.compass.models.Transaction;
import br.com.compass.models.Users;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

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

    public static void addBalance(Long accountId, double value) {
        try {
            em.getTransaction().begin();

            //pega conta e seta valor
            Account account = em.find(Account.class, accountId);
            if (account == null) {
                throw new RuntimeException("Account not found.");
            }else{
                double newBalance = account.getBalance() + value;
                account.setBalance(newBalance);

                Transaction transaction = new Transaction();
                transaction.setAccountId(accountId);
                transaction.setTransactionType(String.valueOf(TransactionsType.DEPOSITS));
                transaction.setAmount(value);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public static double getBalance(Long accountId) {
        return 2; //fazer aq o resto do balance
    }
}