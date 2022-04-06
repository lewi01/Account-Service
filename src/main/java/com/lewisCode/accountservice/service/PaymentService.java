package com.lewisCode.accountservice.service;

import com.lewisCode.accountservice.entity.Payment;
import com.lewisCode.accountservice.entity.SignUp;
import com.lewisCode.accountservice.exeptions.WrongPaymentException;
import com.lewisCode.accountservice.repository.PaymentRepository;
import com.lewisCode.accountservice.repository.SignUpRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final SignUpRepository signUpRepository;

    public void createPayment(List<Payment> payments){
        if (payments.stream().distinct().count() < payments.size()) {
            throw new WrongPaymentException("Error");
        }
        payments.forEach(payment -> {
            Payment userExists = paymentRepository.findByPeriod(payment.getPeriod());
            if (userExists != null ){
                throw  new WrongPaymentException("Error!");
            }
        });
        payments.forEach(payment -> {
            Optional<SignUp> sign = signUpRepository.findByEmail(payment.getEmployee());
            if (sign.isEmpty()){
                throw new WrongPaymentException("Error!");
            }
            payment.setPeriod(payment.getPeriod());
            paymentRepository.save(payment);
        });
    }
    @Transactional
    public void updatePayment(Payment payment){

        if (paymentRepository.findById(payment.getId()).isPresent()){
            payment.setEmployee(payment.getEmployee());
            payment.setPeriod(payment.getPeriod());
            payment.setSalary(payment.getSalary());
            paymentRepository.save(payment);
        }
        throw new WrongPaymentException("Error!");

    }

}
