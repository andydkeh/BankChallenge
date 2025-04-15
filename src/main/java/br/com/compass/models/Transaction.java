package br.com.compass.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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
    private String transactionType;
    
    private double amount;

    @CreationTimestamp
    @Column(name = "transaction_date", insertable = false, updatable = false)
    private Timestamp transactionDate;
    
    @Column(name = "transfers_id")
    private Long transfersId;
} 