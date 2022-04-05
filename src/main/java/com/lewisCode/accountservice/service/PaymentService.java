package com.lewisCode.accountservice.service;

import com.lewisCode.accountservice.entity.Payment;
import com.lewisCode.accountservice.exeptions.WrongPaymentException;
import com.lewisCode.accountservice.repository.PaymentRepository;
import com.lewisCode.accountservice.repository.SignUpRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final SignUpRepository signUpRepository;

    public void createPayment(List<Payment> payments){
        payments.forEach(payment -> {
            boolean userExists = paymentRepository.findByPeriod(payment.getPeriod());
            if (userExists){
                throw  new WrongPaymentException();
            }
        });
        payments.forEach(payment -> {
            payment.setPeriod(payment.getPeriod());
            payment.setSalary(payment.getSalary());
            paymentRepository.save(payment);
        });
    }

}
