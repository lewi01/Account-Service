package com.lewisCode.accountservice.repository;

import com.lewisCode.accountservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

      @Query(value = "INSERT INTO payment(SIGNUP_ID,PERIOD,SALARY) SELECT SIGNUP_ID, ?1,?2 FROM WHERE SIGNUP.EMAIL = ?3 ",
              nativeQuery = true)
      void insertPayments(Date period,Long salary,String email);

      boolean existsByPeriod(Date period);

      @Modifying
      @Transactional
      @Query(value = "UPDATE payment SET SALARY = ?3 WHERE SIGNUP_ID = SELECT ID FROM SIGNUP WHERE SIGNUP.EMAIL = ?1 AND PERIOD = ?2",
              nativeQuery = true)
      Long updatePayment(String email, Date period, long salary);

}
