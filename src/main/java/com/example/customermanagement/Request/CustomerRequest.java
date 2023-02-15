package com.example.customermanagement.Request;

import com.example.customermanagement.customvalidation.BirthDate;
import com.example.customermanagement.enums.Gender;
import com.example.customermanagement.enums.MaritalStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CustomerRequest {
    @NotBlank(message = "First Name is required")
    @Pattern.List({
            @Pattern(regexp="^(?i)[A-Za-z]*$",message = "First name should be alphabet"),
            //@Pattern(regexp = "/^((?!\\s{1,}).)*$/",message = "No white space allowed")
    })
    private String firstName;
    @Pattern(regexp="^(?i)[A-Za-z]*$",message = "Middle name should be alphabet")
    private String middleName;
    @NotBlank(message = "Last Name is required")
    @Pattern(regexp="^(?i)[A-Za-z]*$",message = "Last name should be alphabet")
    private String lastName;
//    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Last Name is required")
    private String gender;
    //@Pattern(regexp="^(?:19\\d{2}|20[01][0-9]|2022)-(?:0[1-9]|1[012])-(?:0[1-9]|[12][0-9]|3[01])$",message = "Date Format is not acceptable")
    @NotNull(message = "The date of birth is required.")
    private String dateOfBirth;
    @NotNull(message = "Citizenship is required")
    @Pattern(regexp = "^[0-9]+(-[0-9]+)+$")
    //@Pattern(regexp="^[A-Za-z0-9]*$",message = "Citizenship number must be in this format")
    private String citizenshipNumber;
    @NotBlank(message = "Address is required")
    private String address;
//    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Marital Status is required")
    private String maritalStatus;
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\\\.[a-z]{2,4}$",message = "This is not required format of email. It should contain @ and .com")
    //@Email(message = "Email must contain @")
    private String email;
    @NotNull(message = "Mobile Number is required")
    @Pattern(regexp="^(98|97)[0-9]{8}$",message = "10 digits is required")
    private String mobileNumber;

    List<CustomerFamilyDetailsRequest> customerFamilyDetailsRequest=new ArrayList<>();
}
