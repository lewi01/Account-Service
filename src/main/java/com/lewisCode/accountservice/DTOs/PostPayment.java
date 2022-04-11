package com.lewisCode.accountservice.DTOs;

import com.lewisCode.accountservice.Validation.PeriodValidation;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PostPayment {

    @Email(regexp = "[\\w.]+(@acme.com)$",message = "You must enter a valid email" +
            " or Email must be from acme.com domain")
    @NotBlank(message = "Email must not be null!")
    private String employee;

    @PeriodValidation(pattern ="MM-yyyy")
    private String period;

    @Min(value = 1, message = "Salary must be non negative!")
    private long salary;

    public Date getPeriodDate() {
        SimpleDateFormat format = new SimpleDateFormat("MM-yyyy");
        format.setLenient(false);
        Date javaDate = null;
        try {
            javaDate = new Date(format.parse(this.period).getTime());
        } catch (ParseException e) {
            System.out.println(period + " is Invalid Date format");
        }
        return javaDate;
    }
}
