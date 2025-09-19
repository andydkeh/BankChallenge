package br.com.andydkeh.controller;

import br.com.andydkeh.dto.UserDTO;
import br.com.andydkeh.models.Users;
import br.com.andydkeh.service.UserService;

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

    public void createUserManager(UserDTO userDTO) {
        try{
            userService.createUser(userDTO);
            System.out.println("Manager created.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showUsersBlock(){
        try{
            userService.showUsersBlock();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
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

    public Users takeUserInformation(String email){
        try {
            return userService.takeUserInformationByEmail(email);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
