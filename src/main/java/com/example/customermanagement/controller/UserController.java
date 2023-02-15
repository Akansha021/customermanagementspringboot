package com.example.customermanagement.controller;

import com.example.customermanagement.Dto.UserDto;
import com.example.customermanagement.Request.CustomerFamilyDetailsRequest;
import com.example.customermanagement.Request.CustomerRequest;
import com.example.customermanagement.Request.UserRequest;
import com.example.customermanagement.entity.Customer;
import com.example.customermanagement.entity.CustomerFamilyDetails;
import com.example.customermanagement.entity.User;
import com.example.customermanagement.serviceimpl.UserServiceImpl;
import com.example.customermanagement.services.CustomerFamilyDetailService;
import com.example.customermanagement.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerFamilyDetailService customerFamilyDetailService;

    @GetMapping("/findById")
    public ResponseEntity<Object> findCustomerById(@RequestParam int id){
        return customerService.getCustomerById(id);
    }
    @GetMapping("/pages")
    public Page<Customer> getAllCustomerBySorting(@RequestParam int page) {
        //Pageable firstPageWithTwoElements = PageRequest.of(0, 10);
        Page<Customer> customers = customerService.getAllCustomerBySorting(page);

        System.out.println("ABC");
        return customers;
    }
    @PutMapping("/updateById")
    public ResponseEntity<Object> updateCustomerById(@RequestParam int id, @RequestBody @Valid CustomerRequest customerRequest){
        return customerService.updateCustomerById(id,customerRequest);
    }
    @PostMapping("/saveCustomer")
    public ResponseEntity<Object> saveCustomer(@RequestBody @Valid CustomerRequest req){
        return customerService.saveCustomer(req);
    }
    @PatchMapping("/statusChange")
    public String updateCustomerStatus(@RequestParam int id,@RequestBody String req){
        return customerService.updateCustomerStatus(id,req);
    }
    @PatchMapping("/deleteCustomer")
    public Customer deleteCustomer(@RequestParam int id,@RequestBody boolean customerRequest){
        return customerService.deleteCustomer(id,customerRequest);
    }


    /*
    @PatchMapping("/deleteCustomerFamilyDetails")
    public CustomerFamilyDetails deleteCustomerFamily(@RequestParam int id){
        return customerFamilyDetailService.deleteCustomerFamilyDetails(id);
    }
     */
    @GetMapping("/customerSearch")
    public ResponseEntity<Object> getCustomerByName(@RequestParam String firstname){
        return customerService.customerSearch(firstname);
    }
    @GetMapping("/getCustomer")
    public List<Customer> getCustomer(){
        return customerService.getAllCustomer();
    }

}
