package com.lewisCode.accountservice.service;

import com.lewisCode.accountservice.entity.Payment;
import com.lewisCode.accountservice.entity.SignUp;
import com.lewisCode.accountservice.errors.UserNotFoundException;
import com.lewisCode.accountservice.repository.PaymentRepository;
import com.lewisCode.accountservice.repository.SignUpRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final SignUpRepository signUpRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          SignUpRepository signUpRepository) {
        this.paymentRepository = paymentRepository;
        this.signUpRepository = signUpRepository;
    }
    public List<Payment> showPayment(SignUp signUp){
        Optional<SignUp> signUp1 = signUpRepository.findByEmail(signUp.getEmail());
        if (signUp1.isEmpty()){
            throw new UserNotFoundException();
        }
        return paymentRepository.findAll();

    }
}
