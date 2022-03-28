package com.lewisCode.accountservice.DTOs;

import com.lewisCode.accountservice.Validation.PeriodValidation;
import lombok.Data;

@Data
public class PostPaymentDTO {

    private String employee;
    @PeriodValidation
    private Data period;
    private Long salary;

}
