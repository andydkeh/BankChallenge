package br.com.compass.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "refund_requests")
public class RefundRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "transaction_id")
    private Long transactionId;
    
    @Column(name = "request_date", insertable = false)
    private Timestamp requestDate;

    @Column(insertable = false)
    private String status;
} 