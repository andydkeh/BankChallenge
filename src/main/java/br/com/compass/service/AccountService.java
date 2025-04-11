package br.com.compass.service;

import br.com.compass.dao.AccountDAO;
import br.com.compass.enums.AccountStatus;
import br.com.compass.models.Account;
import org.mindrot.jbcrypt.BCrypt;
import br.com.compass.dao.UserDAO;
import br.com.compass.models.Users;
import br.com.compass.models.Account;

import java.util.Date;

public class AccountService {
    private final AccountDAO accountDAO;
    private final UserDAO userDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
        this.userDAO = new UserDAO();
    }

    public void createAccount(String name, Date birthDate, String cpf, String phone, String account_type, String password, String email) {
        if (validateEmail(email) == null){
            throw new RuntimeException("Email already exists.");
        }
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        Users user = new Users();
        user.setName(name);
        user.setCpf(cpf);
        user.setPhone(phone);
        user.setBirthDate(new java.sql.Date(birthDate.getTime()));
        user.setPasswordHash(hash);
        user.setEmail(email);
        user.setRole(account_type);
        userDAO.save(user);

        Long idUserAccount = user.getId();
        Account account = new Account();
        account.setUserId(idUserAccount);
        account.setAccountTypeId(account_type);
        account.setStatus(AccountStatus.ACTIVE.name());
        accountDAO.save(account);
    }

    public Users validateEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public void blockAccount(String email) {
        Users user = userDAO.findByEmail(email);
        // add set de bloquear conta aqui
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
