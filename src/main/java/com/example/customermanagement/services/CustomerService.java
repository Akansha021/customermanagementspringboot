package com.example.customermanagement.services;

import com.example.customermanagement.Request.CustomerRequest;
import com.example.customermanagement.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {

    ResponseEntity<Object> saveCustomer(CustomerRequest customerRequest);

    String updateCustomerStatus(int id,String customerRequest);
    List<Customer> getAllCustomer();

    ResponseEntity<Object> getCustomerById(int id);
    Customer deleteCustomer(int id,boolean customerRequest);

    ResponseEntity<Object> updateCustomerById(int id, CustomerRequest customerRequest);

    ResponseEntity<Object> customerSearch(String name);
    Page<Customer> getAllCustomerBySorting(int page);
    //List<Customer> searchCustomerById(int id);


}
