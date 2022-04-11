package com.lewisCode.accountservice.repository;

import com.lewisCode.accountservice.entity.Payment;
import com.lewisCode.accountservice.exeptions.WrongPaymentException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
      @Modifying
      @Query(value = "INSERT INTO payment(PERIOD,SALARY, USER_ID) SELECT ?1,?2,user.ID FROM user WHERE user.EMAIL = ?3 ",
              nativeQuery = true)
      void insertPayments(Date period,Long salary,String email);

      boolean existsByPeriod(Date period);

      @Modifying
      @Transactional
      @Query(value = "UPDATE payment SET SALARY = ?3 WHERE ID = (SELECT ID FROM user WHERE user.EMAIL = ?1) AND PERIOD = ?2 ",
              nativeQuery = true)
      int updatePayment(String email, Date period, long salary);
      List<Payment> findPaymentsByUser_EmailOrderByPeriodDesc(String email);
      List<Payment> findByPeriodAndUser_Email(Date period,String email);
      default java.sql.Date stringToDate(String date, String pattern) {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            format.setLenient(false);
            java.sql.Date javaDate;
            try {
                  javaDate = new java.sql.Date(format.parse(date).getTime());
            } catch (ParseException e) {
                  throw new WrongPaymentException("Error!");
            }
            return javaDate;
      }
}
