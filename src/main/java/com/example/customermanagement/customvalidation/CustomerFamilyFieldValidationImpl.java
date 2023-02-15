package com.example.customermanagement.customvalidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CustomerFamilyFieldValidationImpl implements ConstraintValidator<CustomerFamilyFieldValidation, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        List<String> relationship= Arrays.asList("Father", "Mother", "Grand Father", "Spouse");
        return false;
    }
}
