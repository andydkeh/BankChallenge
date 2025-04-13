package br.com.compass.controller;

import br.com.compass.service.UserService;

import java.util.List;

public class UserController {
    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public List<Long> loginValidation (String email, String password) {
        try {
            return userService.login(email, password);
        } catch (RuntimeException e) {
            return null;
        }
    }

    public String validateScreenByUser(List<Long> IDs){
        return userService.validateRoleByID(IDs.getFirst());

    }

    public void createAdministrator() {
        try {
            userService.createAdministrator();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
