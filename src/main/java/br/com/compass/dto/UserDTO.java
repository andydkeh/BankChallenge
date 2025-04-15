package br.com.compass.dto;

import java.sql.Date;

public record UserDTO(String name, Date birthDate, String cpf, String phone, String email, String passwordHash, String role) {}