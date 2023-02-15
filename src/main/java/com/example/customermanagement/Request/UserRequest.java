package com.example.customermanagement.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
public class UserRequest {
    @NotBlank(message = "Username must not be empty")
    private String username;
    @NotBlank(message = "Password must not be empty")
    private String password;
//    CustomerRequest customerRequest=new CustomerRequest();


}
