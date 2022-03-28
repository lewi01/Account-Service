package com.lewisCode.accountservice.Validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PeriodValidator implements ConstraintValidator<PeriodValidation,String> {

    private final SimpleDateFormat format = new SimpleDateFormat("MM-yyyy");

    @Override
    public void initialize(PeriodValidation constraintAnnotation) {

    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = false;
        if (date == null || date.trim().equals("")){
            return false;
        }else {
            try {
                format.parse(date);
                format.setLenient(false);
               valid=true;
            }catch (ParseException e){
                e.printStackTrace();
            }
        }
        return valid;
    }
}
