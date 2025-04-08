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
    
    private String name;
    
    @Column(name = "birth_date")
    private Date birthDate;
    
    @Column(unique = true)
    private String cpf;
    
    private String phone;
    
    @Column(unique = true)
    private String email;
    
    @Column(name = "password_hash")
    private String passwordHash;
    
    @Column(name = "role_id")
    private int roleId;
    
    private String status;
}
