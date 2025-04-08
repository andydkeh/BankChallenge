package br.com.compass.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "transfers")
public class Transfers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "source_account_id")
    private int sourceAccountId;
    
    @Column(name = "destination_account_id")
    private int destinationAccountId;
    
    private double amount;
    
    @Column(name = "transfer_date")
    private Timestamp transferDate;
    
    private String status;
}
