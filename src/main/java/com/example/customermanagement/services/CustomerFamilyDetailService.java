package com.example.customermanagement.services;

import com.example.customermanagement.Request.CustomerFamilyDetailsRequest;
import com.example.customermanagement.entity.Customer;
import com.example.customermanagement.entity.CustomerFamilyDetails;

import java.util.List;

public interface CustomerFamilyDetailService {
    List<CustomerFamilyDetails> save(List<CustomerFamilyDetailsRequest> customerFamilyDetailsRequest, Customer customer) throws Exception;
    CustomerFamilyDetails deleteCustomerFamilyDetails(int id);
}
