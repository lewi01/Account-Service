package com.lewisCode.accountservice.controller;

import com.lewisCode.accountservice.entity.MySignUpDetailService;
import com.lewisCode.accountservice.entity.Payment;
import com.lewisCode.accountservice.entity.SignUp;
import com.lewisCode.accountservice.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {
    private  final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/empl/payment")
    public void allPayment(@RequestBody SignUp sign){
          paymentService.showPayment(sign);

    }
}
