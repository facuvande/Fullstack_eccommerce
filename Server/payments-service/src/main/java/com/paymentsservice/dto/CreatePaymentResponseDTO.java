package com.paymentsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentResponseDTO {
    private String SandboxInitPoint;
    private Long payment_id;
}
