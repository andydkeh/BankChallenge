package br.com.compass.service;

import br.com.compass.dao.AccountDAO;
import br.com.compass.dao.UserDAO;
import br.com.compass.enums.LoginRespost;
import br.com.compass.enums.RoleType;
import br.com.compass.enums.UserStatus;
import br.com.compass.models.Account;
import br.com.compass.models.Users;
import org.mindrot.jbcrypt.BCrypt;
import br.com.compass.config.ConfigReader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class UserService {
    private final UserDAO userDAO;
    private final AccountDAO accountDAO;

    public UserService() {
        this.userDAO = new UserDAO();
        this.accountDAO = new AccountDAO();
    }

    public Users createUser(String name, Date birthDate, String cpf, String phone, String accountType, String password, String email, String role) {
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        Users user = new Users();
        user.setName(name);
        user.setCpf(cpf);
        user.setPhone(phone);
        user.setBirthDate(new java.sql.Date(birthDate.getTime()));
        user.setPasswordHash(hash);
        user.setEmail(email);
        user.setRole(role);
        userDAO.save(user);

        return user;
    }

    public void createAdministrator(){
        if (UserDAO.findAdministrator() == null){
            createUser(ConfigReader.getAdminName(), null, null, null, null, ConfigReader.getAdminPassword(), ConfigReader.getAdminEmail(), RoleType.ADMINISTRATOR.name());
        } else {
            throw new RuntimeException("Administrator already exists!!");
        }
    }

    public List<Long> login (String email, String password) {
        List<Users> user = userDAO.findByEmail(email);

        if (user == null){
             System.out.println(LoginRespost.USER_NOT_FOUND.getMessage());
             throw new RuntimeException("User not found!");
        }

        var userFinal = user.getFirst();

        if (Objects.equals(userFinal.getStatus(), UserStatus.BLOCKED.name())) {
            System.out.println(LoginRespost.USER_BLOCKED.getMessage());
            throw new RuntimeException("User is blocked!");
        } else if (!BCrypt.checkpw(password, userFinal.getPasswordHash())) {
            userFinal.setPasswordAttemptCounter(userFinal.getPasswordAttemptCounter() + 1);
            if (userFinal.getPasswordAttemptCounter() > 3 && !Objects.equals(userFinal.getRole(), RoleType.ADMINISTRATOR.name()) && !Objects.equals(userFinal.getRole(), RoleType.MANAGER.name())) {
                userFinal.setStatus(UserStatus.BLOCKED.name());
                System.out.println(LoginRespost.USER_BLOCKED.getMessage());
                throw new RuntimeException("User blocked!");
            }else{
                System.out.println(LoginRespost.WRONG_PASSWORD.getMessage());
                throw new RuntimeException("Wrong password!");
            }
        }

        System.out.println(LoginRespost.SUCCESS.getMessage());

        return findAccountsByEmail(email);
    }

    public List<Long> findAccountsByEmail(String email) {

        List<Users> userAccounts = userDAO.findByEmail(email);
        List<Long> idAccounts = new ArrayList<>();
        for (Users userAccount : userAccounts) {
            idAccounts.add(userAccount.getId());
        }
        return idAccounts;
    }

    public String validateUserRole(String email){
        List<Users> user = userDAO.findByEmail(email);

        return user.getFirst().getRole();
    }

    public String validateRoleByID(Long id){
        Account account = accountDAO.findByUserID(id);
        Users user = userDAO.findById(account.getUserId());
        return user.getRole();
    }

//    public boolean validateAccountType(String email, String accountType) {
//        Long idAccount = userDAO.findByEmail(email).getId();
//        String accountTypeValue = accountDAO.findByUserID(idAccount).getAccountType();
//        if (accountType.equals(accountTypeValue)){
//            return true;
//        }
//        return false;
}