package com.lewisCode.accountservice.controller;

import com.lewisCode.accountservice.DTOs.GetPayment;
import com.lewisCode.accountservice.DTOs.PostPayment;
import com.lewisCode.accountservice.entity.MyUserDetailService;
import com.lewisCode.accountservice.repository.PaymentRepository;
import com.lewisCode.accountservice.service.PaymentService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Validated
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    @PostMapping("/acct/payments")
    public ResponseEntity<?> addPayment(@Valid @RequestBody List<@Valid PostPayment> payments){
        paymentService.createPayment(payments);
        return ResponseEntity.ok(Map.of("status:", "Added successfully!"));
    }
    @PutMapping("/acct/payments")
    public ResponseEntity<?> updatePayment(@Valid @RequestBody PostPayment payments){
        paymentService.updatePayment(payments);
        return ResponseEntity.ok(Map.of("status:", "Updated successfully!"));
    }
    @GetMapping("/empl/payment")
    public ResponseEntity<?>getPayments(@AuthenticationPrincipal MyUserDetailService
                                                    userDetailService,
                                        @RequestParam(name = "period", required = false)
                                                String period
                                        ){
        if (period != null){
            Date date = paymentRepository.stringToDate(period,"MM-yyyy");
            GetPayment paymentDTO = paymentService.selectPayRollByPeriod(date);
            return  new ResponseEntity<>(paymentDTO, HttpStatus.OK);
        }
        List<GetPayment> paymentDTOS = paymentService
                .selectAllPaymentPayRoll(userDetailService.getUsername());
        return new ResponseEntity<>(paymentDTOS, HttpStatus.OK);
    }
}
