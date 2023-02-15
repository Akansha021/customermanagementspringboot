package com.example.customermanagement.Dto;

import com.example.customermanagement.Request.CustomerFamilyDetailsRequest;
import com.example.customermanagement.Request.CustomerRequest;
import com.example.customermanagement.Request.UserRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerAllDto {
   CustomerFamilyDetailsRequest customerFamilyDetailsRequest;
   CustomerRequest customerRequest;
}
