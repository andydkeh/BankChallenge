package br.com.compass.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;
    
    @Column(nullable = false, length = 11)
    private String cpf;
    
    @Column(length = 15)
    private String phone;
    
    @Column(nullable = false)
    private String email;
    
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    
    @Column(nullable = false)
    private String role;
    
    @Column(length = 20)
    private String status;

    @Transient
    private Integer passwordAttemptCounter;
}
