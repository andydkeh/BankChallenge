package br.com.andydkeh.models;

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
    private Long sourceAccountId;
    
    @Column(name = "destination_account_id")
    private Long destinationAccountId;
    
    private double amount;
    
    @Column(name = "transfer_date", insertable = false, updatable = false)
    private Timestamp transferDate;
}