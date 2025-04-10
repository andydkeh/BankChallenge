package br.com.compass.controller;

import br.com.compass.service.AccountService;

public class AccountController {
    private final AccountService accountService;

    public AccountController() {
        this.accountService = new AccountService();
    }

    public void deposit(Long accountId, double amount) {
        try {
            accountService.deposit(accountId, amount);
            System.out.println("Done");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void checkBalance(Long accountId) {
        try {
            double balance = accountService.checkBalance(accountId);
            System.out.println("Current balance: R$ " + balance);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
} 