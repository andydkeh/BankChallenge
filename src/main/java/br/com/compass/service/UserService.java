package br.com.compass.service;

import br.com.compass.dao.AccountDAO;
import br.com.compass.dao.UserDAO;
import br.com.compass.dto.UserDTO;
import br.com.compass.enums.LoginResponse;
import br.com.compass.enums.RoleType;
import br.com.compass.enums.UserStatus;
import br.com.compass.models.Account;
import br.com.compass.models.Users;
import org.mindrot.jbcrypt.BCrypt;
import br.com.compass.config.ConfigReader;
import java.sql.Date;

import java.util.Objects;

public class UserService {

    private final UserDAO userDAO;
    private final AccountDAO accountDAO;

    public UserService() {
        this.userDAO = new UserDAO();
        this.accountDAO = new AccountDAO();
    }

    public void createAdministrator(){
        if (UserDAO.findAdministrator() == null){
            UserDTO userDTO = new UserDTO(ConfigReader.getAdminName(),
                    new Date(0,0,0),
                    ConfigReader.getAdminCpf(),
                    null,
                    ConfigReader.getAdminEmail(),
                    ConfigReader.getAdminPassword(),
                    RoleType.ADMINISTRATOR.name());

            createUser(userDTO);
        }
    }

    public Users createUser(UserDTO userDTO) {
        if (userDAO.findByEmail(userDTO.email()) == null) {
            Users user = new Users(userDTO.name(), userDTO.birthDate(), userDTO.cpf(), userDTO.phone(), userDTO.passwordHash(), userDTO.email(), userDTO.role());
            userDAO.save(user);

            return user;
        } else{
            return null;
        }
    }

    public Long login (String email, String password) {
        Users user = userDAO.findByEmail(email);

        if (user == null){
             System.out.println(LoginResponse.USER_NOT_FOUND.getMessage());
             throw new RuntimeException("User not found!");
        }

        if (Objects.equals(user.getStatus(), UserStatus.BLOCKED.name())) {
            System.out.println(LoginResponse.USER_BLOCKED.getMessage());
            throw new RuntimeException("User is blocked!");
        } else if (!BCrypt.checkpw(password, user.getPasswordHash())) {
            user.setPasswordAttemptCounter(user.getPasswordAttemptCounter() + 1);
            if (user.getPasswordAttemptCounter() >= 3 && !Objects.equals(user.getRole(), RoleType.ADMINISTRATOR.name()) && !Objects.equals(user.getRole(), RoleType.MANAGER.name())) {
                user.setStatus(UserStatus.BLOCKED.name());
                userDAO.update(user);
                System.out.println(LoginResponse.USER_BLOCKED.getMessage());
                throw new RuntimeException("User blocked!");
            }else{
                System.out.println(LoginResponse.WRONG_PASSWORD.getMessage());
                throw new RuntimeException("Wrong password!");
            }
        }

        System.out.println(LoginResponse.SUCCESS.getMessage());

        return findAccountsByEmail(email);
    }

    public Long findAccountsByEmail(String email) {
        Users userAccounts = userDAO.findByEmail(email);
        return userAccounts.getId();
    }

    public String validateRoleByID(String email){
        Users user = userDAO.findByEmail(email);
        return user.getRole();
    }

    public boolean validateAccountType(String email, String accountType) {
        Long idAccount = userDAO.findByEmail(email).getId();
        Account accountTypeValue = accountDAO.findByUserID(idAccount);

        if (accountTypeValue != null){
            return accountTypeValue.getAccountType().equals(accountType);
        }else{
            return false;
        }
    }

    public void showUsersBlock(){
        var usersBlock = userDAO.findAllUsersBlock();

        if (usersBlock.isEmpty()){
            throw new RuntimeException("No users blocked at this time.");
        }

        for (Users user : usersBlock){
            System.out.println("======= User list =========");
            System.out.println("|ID: " + user.getId() + " |EMAIL:"+user.getEmail());
        }
    }

    public void unlockUser(Long userId){
        Users user = userDAO.findById(userId);
        user.setStatus(UserStatus.ACTIVE.name());
        userDAO.save(user);

        System.out.println("User unlocked!");
    }

    public Users takeUserInformationByEmail(String email){
       return userDAO.findByEmail(email);
    }
}