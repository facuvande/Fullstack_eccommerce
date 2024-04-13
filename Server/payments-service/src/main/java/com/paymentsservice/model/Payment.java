package com.paymentsservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long payment_id;
    private String collectionId;
    private String collectionStatus;
    private String externalReference;
    private String paymentType;
    private String merchantOrderId;
    private String preferenceId;
    private String siteId;
    private String processingMode;
    private String merchantAccountId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date payment_date;

}
