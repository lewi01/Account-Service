package com.lewisCode.accountservice.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Getter
@Setter
public class GetPaymentDTO {
    private String name;
    private String lastname;
    private String period;
    private String salary;

    public void setPeriod(Date period){
       // this.period = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-yyyy", Locale.ENGLISH);
        this.period = simpleDateFormat.format(period);
    }
}
