package com.example.customermanagement.Request;

import com.example.customermanagement.customvalidation.CustomerFamilyFieldValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerFamilyDetailsRequest {
    @NotBlank(message = "This field must not be empty")
    @CustomerFamilyFieldValidation
    private String relationship;
    @NotBlank(message = "This field must not be empty")
    private String relationPersonName;

}
