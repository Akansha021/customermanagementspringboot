package com.example.customermanagement.customvalidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = CustomerFamilyFieldValidationImpl.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface CustomerFamilyFieldValidation {

    String message() default "{com.example.customermanagement.customvalidation.CustomFaamilyFieldValidation.message}";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}
