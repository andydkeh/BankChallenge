package br.com.compass.controller;

import br.com.compass.models.Users;
import br.com.compass.service.UserService;

import java.util.Date;
import java.util.List;

public class UserController {
    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public void createAdministrator() {
        try {
            userService.createAdministrator();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void createUserManager(String name, Date birthDate, String cpf, String phone, String password, String email, String role) {
        try{
            userService.createUser(name, birthDate, cpf, phone, password, email, role);
            System.out.println("Manager created.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean showUsersBlock(){
        try{
            return userService.showUsersBlock();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void unlockUser(Long userId) {
        try{
            userService.unlockUser(userId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Long loginValidation (String email, String password) {
        try {
            return userService.login(email, password);
        } catch (RuntimeException e) {
            return null;
        }
    }

    public String validateScreenByUser(String email){
        return userService.validateRoleByID(email);
    }
}
