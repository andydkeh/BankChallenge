package br.com.compass.controller;

import br.com.compass.service.UserService;

public class UserController {
    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public boolean login (String email, String password) {
        return userService.login(email, password);
    }
}
