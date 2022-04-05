package com.lewisCode.accountservice.repository;

import com.lewisCode.accountservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    boolean findByPeriod(String period);
//    Long updatePayment(Long salary);
//    List<Payment> findAllByEmail(String email);
//    Payment findByPeriod(Date date);

}
