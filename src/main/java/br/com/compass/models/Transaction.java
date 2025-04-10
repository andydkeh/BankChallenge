package br.com.compass.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "account_id")
    private Long accountId;
    
    @Column(name = "transaction_type")
    private int transactionType;
    
    private double amount;
    
    @Column(name = "transaction_date")
    private Timestamp transactionDate;
    
    private String status;
    
    private String description;
    
    @Column(name = "transfers_id")
    private Integer transfersId;
} 