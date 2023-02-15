package com.example.customermanagement.customvalidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Calendar;
import java.util.Date;

public class BirthDateValidator implements ConstraintValidator<BirthDate, Date> {
    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        Calendar dateInCalendar = Calendar.getInstance();
        dateInCalendar.setTime(date);

        return Calendar.getInstance().get(Calendar.YEAR) - dateInCalendar.get(Calendar.YEAR) >= 18;
    }
}
