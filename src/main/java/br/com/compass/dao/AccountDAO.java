package br.com.compass.dao;

import br.com.compass.enums.TransactionsType;
import br.com.compass.models.Account;
import br.com.compass.models.Transaction;

public class AccountDAO extends BaseDAO<Account>{
    public AccountDAO() {
        super(Account.class);
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