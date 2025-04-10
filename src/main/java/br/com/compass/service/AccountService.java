package br.com.compass.service;

import br.com.compass.dao.AccountDAO;
import br.com.compass.models.Account;

public class AccountService {
    private final AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public void deposit(Long accountId, double amount) {
        try {
            accountDAO.addBalance(accountId, amount);
        } catch (Exception e) {
            throw new RuntimeException("Erro: " + e.getMessage());
        }
    }

    public double checkBalance(Long accountId) {
        try {
            return accountDAO.getBalance(accountId);
        } catch (Exception e) {
            throw new RuntimeException("Erro: " + e.getMessage());
        }
    }
}
