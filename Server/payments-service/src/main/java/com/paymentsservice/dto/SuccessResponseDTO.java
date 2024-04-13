package com.paymentsservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SuccessResponseDTO {
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
}
