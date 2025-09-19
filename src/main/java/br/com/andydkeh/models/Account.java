package br.com.andydkeh.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "account_type")
    private String accountType;
    
    private double balance;

    @Column(insertable = false)
    private String status;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Date created_at;
} 