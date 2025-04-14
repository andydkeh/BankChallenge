package br.com.compass.controller;

import br.com.compass.models.Account;
import br.com.compass.service.AccountService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class AccountController {
    private final AccountService accountService;

    public AccountController() {
        this.accountService = new AccountService();
    }

    public void createAccount(String name, Date birth_date, String cpf, String phone, String account_type, String password, String email, String role){
        try{
            accountService.createAccount(name, birth_date, cpf, phone, account_type, password, email, role);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void blockAccount(String email) {
       // accountService.blockAccount(email);
    }

    public Account showAccountById(Long idAccount) {
        try{
            return accountService.showAccountById(idAccount);
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public List<Account> showAccountsUser(Long idUser) {
        try{
            return accountService.showAccounts(idUser);
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public List<String> validateTypeAccountCreate(Long idUser) {
        try{
            return accountService.validateTypeAccountCreate(idUser);
        }catch(RuntimeException e){
            System.out.println(e);
        }
        return null;
    }

    public void checkBalance(Long accountId) {
        try {
            accountService.checkBalance(accountId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deposit(Account account, double amount) {
        try {
            accountService.deposit(account, amount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void withdraw(Account account, double amount) {
        try {
            accountService.withdraw(account, amount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void transfer(Account fromAccount, Long toAccountId, double amount) {
        try{
            accountService.transfer(fromAccount, toAccountId, amount);
        }catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    public void downloadCSVTransactions(Long idAccount) {

        try {
            String csvContent = accountService.generateCSVTransactions(idAccount);

            String fileName = "transactions_" + idAccount + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss")) + ".csv";

            try {
                Path filePath = Paths.get(fileName);
                Files.writeString(filePath, csvContent);
                System.out.println("File saved as: " + fileName);
            } catch (IOException e) {
                System.out.println("Error saving file: " + e.getMessage());
                throw new RuntimeException("Error saving CSV file", e);
            }
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }
} 