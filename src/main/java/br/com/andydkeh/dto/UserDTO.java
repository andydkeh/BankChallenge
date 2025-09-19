package br.com.andydkeh.dto;

import java.sql.Date;

public record UserDTO(String name, Date birthDate, String cpf, String phone, String email, String passwordHash, String role) {}