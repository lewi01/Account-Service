package com.lewisCode.accountservice.controller;

import com.lewisCode.accountservice.DTOs.PostPaymentDTO;
import com.lewisCode.accountservice.entity.Payment;
import com.lewisCode.accountservice.service.PaymentServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Validated
public class PaymentController {

    private final PaymentServiceImpl paymentService;

    @PostMapping("/acct/payments")
    public ResponseEntity<?> addPayment(@Valid @RequestBody List<@Valid PostPaymentDTO> payments){
        paymentService.createPayment(payments);
        return ResponseEntity.ok(Map.of("status:", "Added successfully!"));
    }
    @PutMapping("/acct/payments")
    public ResponseEntity<?> updatePayment(@Valid @RequestBody PostPaymentDTO payments){
        paymentService.updatePayment(payments);
        return ResponseEntity.ok(Map.of("status:", "Updated successfully!"));
    }
}
