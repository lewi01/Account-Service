package com.lewisCode.accountservice.service;

import com.lewisCode.accountservice.DTOs.GetPayment;
import com.lewisCode.accountservice.DTOs.PostPayment;
import com.lewisCode.accountservice.entity.Payment;
import com.lewisCode.accountservice.exeptions.WrongPaymentException;
import com.lewisCode.accountservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public void createPayment(List<PostPayment> payments){
        if (payments.stream().distinct().count() < payments.size()) {
            throw new WrongPaymentException("Error");
        }
        payments.forEach(payment -> {
            boolean userExists = paymentRepository.existsByPeriod(payment.getPeriodDate());
            if (userExists){
                throw  new WrongPaymentException("Error!");
            }
        });
        payments.forEach(payment -> paymentRepository.insertPayments(payment.getPeriodDate(),
                payment.getSalary(), payment.getEmployee()));
    }
    public void updatePayment(PostPayment payment){
        var count = paymentRepository.updatePayment(payment.getEmployee(),
                payment.getPeriodDate(), payment.getSalary());
        if (count <= 0) {
            throw new WrongPaymentException("Error!");
        }
    }
    public List<GetPayment> selectAllPaymentPayRoll(String email){
        List<Payment> payments = paymentRepository
                .findPaymentsByUser_EmailOrderByPeriodDesc(email);
        return payments.stream().map(payment -> {
            GetPayment getPaymentDTO = new GetPayment();
            getPaymentDTO.setName(payment.getUser().getName());
            getPaymentDTO.setLastname(payment.getUser().getLastname());
            getPaymentDTO.setPeriod((Date) payment.getPeriod());
            getPaymentDTO.setSalary(payment.getSalary());

            return getPaymentDTO;
        }).collect(Collectors.toList());
    }
    public GetPayment selectPayRollByPeriod(Date period, String email){
        Payment payment =
                paymentRepository.findByPeriodAndUser_Email(period,email)
                .stream().findFirst().get();
        GetPayment getPaymentDTO = new GetPayment();
        getPaymentDTO.setName(payment.getUser().getName());
        getPaymentDTO.setLastname(payment.getUser().getLastname());
        getPaymentDTO.setPeriod((Date) payment.getPeriod());
        getPaymentDTO.setSalary(payment.getSalary());
        return getPaymentDTO;
    }

}
