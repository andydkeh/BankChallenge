package br.com.compass.utils;

import java.time.LocalDate;
import java.time.Period;

public class UserValidation {
    public static void validateCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            throw new RuntimeException("CPF must have 11 digits");
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            throw new RuntimeException("Invalid CPF");
        }
    }
    
    public static void validatePhone(String phone) {
        phone = phone.replaceAll("[^0-9]", "");

        if (phone.length() < 10 || phone.length() > 11) {
            throw new RuntimeException("Invalid phone number");
        }
    }
    
    public static void validateBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new RuntimeException("Birth date cannot be null");
        }

        if (birthDate.isAfter(LocalDate.now())) {
            throw new RuntimeException("Birth date cannot be in the future");
        }

        int age = Period.between(birthDate, LocalDate.now()).getYears();

        if (age < 18) {
            throw new RuntimeException("User must be at least 18 years old");
        }

        if (age > 120) {
            throw new RuntimeException("Invalid birth date");
        }
    }

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("Name cannot be null or empty");
        }

        if (name.length() < 3) {
            throw new RuntimeException("Name must have at least 3 characters");
        }

        if (name.length() > 100) {
            throw new RuntimeException("Name must have maximum 100 characters");
        }

        if (!name.matches("^[a-zA-Z\\s]+$")) {
            throw new RuntimeException("Name can only contain letters and spaces");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("Email cannot be null or empty");
        }

        if (email.length() > 100) {
            throw new RuntimeException("Email must have maximum 100 characters");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new RuntimeException("Invalid email format");
        }

        if (email.contains(" ")) {
            throw new RuntimeException("Email cannot contain spaces");
        }
    }
} 