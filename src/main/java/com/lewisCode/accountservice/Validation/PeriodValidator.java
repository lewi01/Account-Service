package com.lewisCode.accountservice.Validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PeriodValidator implements ConstraintValidator<PeriodValidation,String> {

    private String pattern;

    @Override
    public void initialize(PeriodValidation constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null){
            return true;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        dateFormat.setLenient(false);
            try {
                dateFormat.parse(date);
                return true;
            }catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
    }
}
