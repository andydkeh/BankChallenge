package br.com.compass.dao;

import br.com.compass.models.Account;
import br.com.compass.models.Transaction;

import java.math.BigDecimal;

public class AccountDAO extends BaseDAO<AccountDAO>{
    public AccountDAO() {
        super(AccountDAO.class);
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
                transaction.setTransactionType(2);
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