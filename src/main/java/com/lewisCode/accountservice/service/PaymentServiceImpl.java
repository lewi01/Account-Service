package com.lewisCode.accountservice.service;

import com.lewisCode.accountservice.DTOs.PostPaymentDTO;
import com.lewisCode.accountservice.entity.Payment;
import com.lewisCode.accountservice.exeptions.WrongPaymentException;
import com.lewisCode.accountservice.repository.PaymentRepository;
import com.lewisCode.accountservice.repository.SignUpRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PaymentServiceImpl {

    private final PaymentRepository paymentRepository;
    private final SignUpRepository signUpRepository;

    public void createPayment(List<PostPaymentDTO> payments){
        if (payments.stream().distinct().count() < payments.size()) {
            throw new WrongPaymentException("Error");
        }
        payments.forEach(payment -> {
            boolean userExists = paymentRepository.existsByPeriod(payment.getPeriodDate());
            if (userExists){
                throw  new WrongPaymentException("Error!");
            }
        });
        payments.forEach(payment -> paymentRepository.insertPayments(payment.getPeriodDate(), payment.getSalary(), payment.getEmployee()));
    }
    public void updatePayment(PostPaymentDTO payment){
        var count = paymentRepository.updatePayment(payment.getEmployee(), payment.getPeriodDate(), payment.getSalary());
        if (count <= 0) {
            throw new WrongPaymentException("Error!");
        }
    }

}
