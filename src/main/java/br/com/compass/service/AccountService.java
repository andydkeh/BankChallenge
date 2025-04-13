package br.com.compass.service;

import br.com.compass.dao.AccountDAO;
import br.com.compass.enums.AccountStatus;
import br.com.compass.enums.RoleType;
import br.com.compass.models.Account;
import org.mindrot.jbcrypt.BCrypt;
import br.com.compass.dao.UserDAO;
import br.com.compass.models.Users;
import br.com.compass.models.Account;
import br.com.compass.service.UserService;

import java.util.Date;

public class AccountService {
    private final AccountDAO accountDAO;
    private final UserDAO userDAO;
    private final UserService userService;

    public AccountService() {
        this.accountDAO = new AccountDAO();
        this.userDAO = new UserDAO();
        this.userService = new UserService();
    }

//    public void createAccount(String name, Date birthDate, String cpf, String phone, String accountType, String password, String email, String role) {
//
//        if (userService.validateAccountType(email, accountType)){
//            throw new RuntimeException("Email already registered for type: "+accountType+" account.");
//        }
//        var user = userService.createUser(name, birthDate, cpf, phone, accountType, password, email, role);
//
//        Long idUserAccount = user.getId();
//        Account account = new Account();
//        account.setUserId(idUserAccount);
//        account.setAccountType(accountType);
//        account.setStatus(AccountStatus.ACTIVE.name());
//        accountDAO.save(account);
//    }

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
