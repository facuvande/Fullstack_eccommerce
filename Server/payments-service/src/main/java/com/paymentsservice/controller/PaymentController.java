package com.paymentsservice.controller;

import com.google.gson.GsonBuilder;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import com.paymentsservice.dto.CreatePaymentResponseDTO;
import com.paymentsservice.dto.SuccessResponseDTO;
import com.paymentsservice.model.Payment;
import com.paymentsservice.service.IPaymentService;
import com.paymentsservice.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {

    @Autowired
    private IPaymentService iPaymentService;

    @PostMapping("/saveInformation")
    public ResponseEntity<SuccessResponseDTO> saveInformation(@RequestParam Long payment_id, @RequestBody Payment payment){
        Payment myPayment = iPaymentService.saveInformation(payment_id, payment);

        SuccessResponseDTO successResponseDTO = new SuccessResponseDTO();
        successResponseDTO.setCollectionId(myPayment.getCollectionId());
        successResponseDTO.setCollectionStatus(myPayment.getCollectionStatus());
        successResponseDTO.setExternalReference(myPayment.getExternalReference());
        successResponseDTO.setPaymentType(myPayment.getPaymentType());
        successResponseDTO.setMerchantOrderId(myPayment.getMerchantOrderId());
        successResponseDTO.setPreferenceId(myPayment.getPreferenceId());
        successResponseDTO.setSiteId(myPayment.getSiteId());
        successResponseDTO.setProcessingMode(myPayment.getProcessingMode());
        successResponseDTO.setMerchantAccountId(myPayment.getMerchantAccountId());

        return new ResponseEntity<>(successResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/createAndRedirect")
    public ResponseEntity<CreatePaymentResponseDTO> createAndRedirect(@RequestParam double total) throws MPException {
        Preference preference = new Preference();

        preference.setBackUrls(
                new BackUrls()
                        .setFailure("http://localhost:5173/failure")
                        .setPending("http://localhost:5173/pending")
                        .setSuccess("http://localhost:5173/success")
        );

        Item item = new Item();
        item.setTitle("Compra de productos")
                .setQuantity(1)
                .setUnitPrice((float) total);
        preference.appendItem(item);

        var result = preference.save();

        Payment payment = iPaymentService.createPayment(new Payment());

        CreatePaymentResponseDTO createPaymentResponseDTO = new CreatePaymentResponseDTO();
        createPaymentResponseDTO.setPayment_id(payment.getPayment_id());
        createPaymentResponseDTO.setSandboxInitPoint(result.getSandboxInitPoint());

        return new ResponseEntity<>(createPaymentResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/success")
    public ResponseEntity<SuccessResponseDTO> success(HttpServletRequest request,
                                                      @RequestParam("collection_id") String collectionId,
                                                      @RequestParam("collection_status") String collectionStatus,
                                                      @RequestParam("external_reference") String externalReference,
                                                      @RequestParam("payment_type") String paymentType,
                                                      @RequestParam("merchant_order_id") String merchantOrderId,
                                                      @RequestParam("preference_id") String preferenceId,
                                                      @RequestParam("site_id") String siteId,
                                                      @RequestParam("processing_mode") String processingMode,
                                                      @RequestParam("merchant_account_id") String merchantAccountId, Model model) throws MPException{
        var payment = com.mercadopago.resources.Payment.findById(collectionId);
        model.addAttribute("payment", payment);

        SuccessResponseDTO successResponseDTO = new SuccessResponseDTO();
        successResponseDTO.setCollectionId(collectionId);
        successResponseDTO.setCollectionStatus(collectionStatus);
        successResponseDTO.setExternalReference(externalReference);
        successResponseDTO.setPaymentType(paymentType);
        successResponseDTO.setMerchantOrderId(merchantOrderId);
        successResponseDTO.setPreferenceId(preferenceId);
        successResponseDTO.setSiteId(siteId);
        successResponseDTO.setProcessingMode(processingMode);
        successResponseDTO.setMerchantAccountId(merchantAccountId);



        return new ResponseEntity<>(successResponseDTO, HttpStatus.OK);

    }

    @GetMapping("/failure")
    public String failure(HttpServletRequest request,
                          @RequestParam("collection_id") String collectionId,
                          @RequestParam("collection_status") String collectionStatus,
                          @RequestParam("external_reference") String externalReference,
                          @RequestParam("payment_type") String paymentType,
                          @RequestParam("merchant_order_id") String merchantOrderId,
                          @RequestParam("preference_id") String preferenceId,
                          @RequestParam("site_id") String siteId,
                          @RequestParam("processing_mode") String processingMode,
                          @RequestParam("merchant_account_id") String merchantAccountId, Model model) throws MPException{
        var payment = com.mercadopago.resources.Payment.findById(collectionId);
        model.addAttribute("payment", payment);
        return "ok";
    }
}
