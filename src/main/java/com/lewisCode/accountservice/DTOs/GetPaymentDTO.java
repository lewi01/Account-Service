package com.lewisCode.accountservice.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Locale;

@Getter
@Setter
public class GetPaymentDTO {
    private String name;
    private String lastname;
    private String period;
    private String salary;

    public void setPeriod(java.sql.Date period) {
        this.period = "";
        SimpleDateFormat format = new SimpleDateFormat("MMMM-yyyy", Locale.ENGLISH);
        this.period = format.format(period);
    }

    public void setSalary(Long salary) {
        Long dollars = salary / 100;
        Long cents = salary % 100;
        this.salary = String.format("%d dollar(s) %d cent(s)",dollars,cents);
    }
}
