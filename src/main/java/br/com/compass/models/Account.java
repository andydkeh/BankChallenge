package br.com.compass.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id")
    private int userId;
    
    @Column(name = "account_type_id")
    private int accountTypeId;
    
    private double balance;
    
    private String status;
} 