package com.example.customermanagement.serviceimpl;

import com.example.customermanagement.Request.CustomerFamilyDetailsRequest;
import com.example.customermanagement.Request.CustomerRequest;
import com.example.customermanagement.entity.Customer;
import com.example.customermanagement.entity.CustomerFamilyDetails;
import com.example.customermanagement.entity.User;
import com.example.customermanagement.enums.Status;
import com.example.customermanagement.repository.CustomerFamilyDetailRepository;
import com.example.customermanagement.repository.CustomerRepository;
import com.example.customermanagement.responses.ResponseStatus;
import com.example.customermanagement.services.CustomerService;

import com.sun.security.auth.UserPrincipal;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;


@Service
public class CustomServiceImpl implements CustomerService {
    @Autowired
    FamilyDetailsServiceImpl familyDetailsService;

    @Autowired
    CustomerFamilyDetailRepository customerFamilyDetailRepository;
    @Autowired
    CustomerRepository customerRepository;

    ResponseStatus responseStatus=new ResponseStatus();

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public ResponseEntity<Object> saveCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        try {
            customer.setFirstName(customerRequest.getFirstName());
            customer.setMiddleName(customerRequest.getMiddleName());
            customer.setLastName(customerRequest.getLastName());
            customer.setGender(customerRequest.getGender());
            LocalDate localDate = LocalDate.now();
            String DOB=customerRequest.getDateOfBirth();
            DateFormat df=new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date date=df.parse(DOB);

            //if(date.before(java.sql.Date.valueOf(localDate)) && date.equals(java.sql.Date.valueOf(localDate))) {
                customer.setDateOfBirth(date);
            //}else{
               // throw new Exception("Date is not valid");
           // }

            customer.setStatus("ACTIVE");

            //customer.setDateOfBirth(customerRequest.getDateOfBirth());
            customer.setCitizenshipNumber(customerRequest.getCitizenshipNumber());
            customer.setAddress(customerRequest.getAddress());
            customer.setMaritalStatus(customerRequest.getMaritalStatus());
            customer.setEmail(customerRequest.getEmail());
            customer.setMobileNumber(customerRequest.getMobileNumber());
            customer.getModifiedBy();

            System.out.println(localDate);
            customer.setAddedDate(java.sql.Date.valueOf(localDate));
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            System.out.println("Cu "+userDetails.getUsername());
            customer.setAddedBy(userDetails.getUsername());

            customerRepository.save(customer);

            List<CustomerFamilyDetails> customerFamilyDetails= new ArrayList<>();
            customerFamilyDetails= familyDetailsService.save(customerRequest.getCustomerFamilyDetailsRequest(), customer);
            customer.setCustomerFamilyDetails(customerFamilyDetails);


        }catch (Exception e){
            System.out.println(e.getMessage());
            ResponseStatus responseStatus=new ResponseStatus();
            HttpStatus request= HttpStatus.INTERNAL_SERVER_ERROR;
            responseStatus.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseStatus.setHttpStatusCode(HttpStatusCode.valueOf(500));
            responseStatus.setMessage("Something went wrong");
            return new ResponseEntity<>(responseStatus,request);
        }
        return ResponseEntity.ok().body(customer);
    }


    @Override
    public Customer deleteCustomer(int id,boolean customerRequest) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if(customer != null) {
            try {

                customer.setFlag(customerRequest);
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                UserDetails userDetails = (UserDetails) auth.getPrincipal();
                customer.setModifiedBy(userDetails.getUsername());
                System.out.println("Flag "+customer.getModifiedBy());

                customerRepository.save(customer);

            } catch (Exception e) {
                System.out.println(e.getMessage());

            }
        }

        return customer;
    }

    @Override
    public String updateCustomerStatus(int id,String customerRequest) {
        Customer customer = customerRepository.findById(id).orElse(null);
        try {

            customer.setStatus(customerRequest);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            customer.setModifiedBy(userDetails.getUsername());

            customerRepository.save(customer);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return customer.getStatus();
    }

    public Page<Customer> getAllCustomerBySorting(int page) {
        //Pageable firstPageWithTwoElements = PageRequest.of(0, 10);
        Page<Customer> customers = customerRepository.findAll(PageRequest.of(page,4));

        System.out.println("ABC");
        return customers;
    }

    @Override
    public List<Customer> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll();

        System.out.println("ABC");
        return customers;
    }

    /*

    @Override
    public List<Customer> searchCustomerById(int id) {

        try {
            System.out.println(customer.getFirstName());
            System.out.println(customer.getEmail());

            List<CustomerFamilyDetails> customerFamilyDetails=customer.getCustomerFamilyDetails();
            System.out.println(customer.getCustomerFamilyDetails());
            for(CustomerFamilyDetails customerFamilyDetail:customerFamilyDetails){
                System.out.println("Fam "+customerFamilyDetail.getRelationship());
                System.out.println("Fam2 "+customerFamilyDetail.getRelationPersonName());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            ResponseStatus responseStatus=new ResponseStatus();
            HttpStatus request= HttpStatus.INTERNAL_SERVER_ERROR;
            responseStatus.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseStatus.setHttpStatusCode(HttpStatusCode.valueOf(500));
            responseStatus.setMessage("Not Success");
            return new ResponseEntity<>(responseStatus,request);
        }
        return customerRepository.findById(id).orElse(null);;
    }

     */

    @Override
    public ResponseEntity<Object> getCustomerById(int id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        try {
            System.out.println(customer.getFirstName());
            System.out.println(customer.getEmail());

            List<CustomerFamilyDetails> customerFamilyDetails=customer.getCustomerFamilyDetails();
            System.out.println(customer.getCustomerFamilyDetails());
            /*
            for(CustomerFamilyDetails customerFamilyDetail:customerFamilyDetails){
                if(customerFamilyDetail.isFlag()) {
                    System.out.println("Fam " + customerFamilyDetail.getRelationship());
                    System.out.println("Fam2 " + customerFamilyDetail.getRelationPersonName());
                }
            }

             */
        }catch (Exception e){
            System.out.println(e.getMessage());
            ResponseStatus responseStatus=new ResponseStatus();
            HttpStatus request= HttpStatus.INTERNAL_SERVER_ERROR;
            responseStatus.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseStatus.setHttpStatusCode(HttpStatusCode.valueOf(500));
            responseStatus.setMessage("Not Success");
            return new ResponseEntity<>(responseStatus,request);
        }
        return ResponseEntity.ok().body(customer);
    }

    @Override
    public ResponseEntity<Object> updateCustomerById(int id, CustomerRequest customerRequest) {
        Customer customer = customerRepository.findById(id).orElse(null);
        System.out.println(customerRequest.getFirstName());
        System.out.println(customerRequest.getMiddleName());


        try {
            if(Objects.equals(customer.getStatus(), "ACTIVE")) {
                customer.setFirstName(customerRequest.getFirstName());
                customer.setMiddleName(customerRequest.getMiddleName());
                customer.setLastName(customerRequest.getLastName());
                customer.setGender(customerRequest.getGender());
                LocalDate today = LocalDate.now();
                System.out.println(today);
                String DOB = customerRequest.getDateOfBirth();
                DateFormat df = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
                Date date = df.parse(DOB);
                customer.setDateOfBirth(date);
                customer.getStatus();
                customer.getAddedBy();
                customer.setCitizenshipNumber(customerRequest.getCitizenshipNumber());
                customer.setAddress(customerRequest.getAddress());
                customer.setMaritalStatus(customerRequest.getMaritalStatus());
                customer.setEmail(customerRequest.getEmail());
                customer.setMobileNumber(customerRequest.getMobileNumber());
                LocalDate localDate = LocalDate.now();
                customer.setModifiedDate(java.sql.Date.valueOf(localDate));
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                UserDetails userDetails = (UserDetails) auth.getPrincipal();
                customer.setModifiedBy(userDetails.getUsername());

                List<CustomerFamilyDetails> customerFamilyDetails = new ArrayList<>();
                List<CustomerFamilyDetails> customerFamilyDetailsRes = customer.getCustomerFamilyDetails();
                List<CustomerFamilyDetailsRequest> customerFamilyDetailsRequest = customerRequest.getCustomerFamilyDetailsRequest();
                List<CustomerFamilyDetails> customerFamilyDetailsList = new ArrayList<>();

                for (CustomerFamilyDetails customerFamilyDetails1 : customerFamilyDetailsRes) {
                    if (customerFamilyDetails1.isFlag()) {
                        customerFamilyDetailsList.add(customerFamilyDetails1);
                    }
                }

                int i = 0;
                for (CustomerFamilyDetailsRequest customerFamilyDetailsInfo : customerFamilyDetailsRequest) {
                    //if(i < customer.getCustomerFamilyDetails().size()) {
                    if (i < customer.getCustomerFamilyDetails().size() && customer.getCustomerFamilyDetails().get(i).isFlag() && (customer.getCustomerFamilyDetails().get(i).getRelationship().equals(customerFamilyDetailsInfo.getRelationship()))) {
                        CustomerFamilyDetails customerFamilyDetailss = customer.getCustomerFamilyDetails().get(i);
                        System.out.println("Id: " + customer.getCustomerFamilyDetails().get(i).getId());
                        customerFamilyDetailss.setId(customer.getCustomerFamilyDetails().get(i).getId());
                        System.out.println("Flag " + customerFamilyDetailss.isFlag());
                        customerFamilyDetailss.setRelationship(customerFamilyDetailsInfo.getRelationship());
                        customerFamilyDetailss.setRelationPersonName(customerFamilyDetailsInfo.getRelationPersonName());
                        customerFamilyDetailss.setCustomer(customer);
                        customerFamilyDetails.add(customerFamilyDetailss);
                        System.out.println("Indeo " + customerFamilyDetails.indexOf(customerFamilyDetailss));
                        i += 1;
                        //}
                    } else {
                        CustomerFamilyDetails customerFamilyDetails1 = new CustomerFamilyDetails();
                        customerFamilyDetails1.setCustomer(customer);
                        customerFamilyDetails1.setRelationship(customerFamilyDetailsInfo.getRelationship());
                        customerFamilyDetails1.setRelationPersonName(customerFamilyDetailsInfo.getRelationPersonName());
                        customerFamilyDetails.add(customerFamilyDetails1);
                    }
                }

                List<Integer> customerDetailRel = new ArrayList();
                List<Integer> customerRequestRel = new ArrayList<>();

                System.out.println("Customer Details Request: ");
                for (CustomerFamilyDetails customerFamilyDetails1 : customerFamilyDetails) {
                    int custId = customerFamilyDetails1.getId();
                    customerDetailRel.add(custId);
                    System.out.println(custId);
                }

                System.out.println("Customer Details: ");
                for (CustomerFamilyDetails customerFamilyDetails1 : customerFamilyDetailsRes) {
                    int custId = customerFamilyDetails1.getId();
                    if (customerFamilyDetails1.isFlag()) {
                        customerRequestRel.add(custId);
                        System.out.println(custId);
                    }
                }

                customerRequestRel.removeAll(customerDetailRel);

                for (Integer customerFamilyDetailsRequest1 : customerRequestRel) {
                    System.out.println("hh 1 " + customerFamilyDetailsRequest1);
                }

                for (Integer customerFamilyDetailsRequest1 : customerDetailRel) {
                    System.out.println("hh 2 " + customerFamilyDetailsRequest1);
                }

                i = 0;
                if (!customerRequestRel.isEmpty() && i < customerRequestRel.size()) {
                    familyDetailsService.deleteCustomerFamilyDetails(customerRequestRel.get(i));
                    System.out.println("Extra " + customerRequestRel);
                    i += 1;
                }

                customer.setCustomerFamilyDetails(customerFamilyDetails);
                customerRepository.save(customer);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            HttpStatus request= HttpStatus.INTERNAL_SERVER_ERROR;
            responseStatus.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseStatus.setHttpStatusCode(HttpStatusCode.valueOf(500));
            responseStatus.setMessage("Not Success");
            return new ResponseEntity<>(responseStatus,request);
        }
        return ResponseEntity.ok().body(customer);
    }

    @Override
    public ResponseEntity<Object> customerSearch(String name) {
        System.out.println("Name: "+name);
        Customer customerName= customerRepository.findByFirstName(name);
        Customer customer = customerRepository.findById(customerName.getId()).orElse(null);
        List<CustomerFamilyDetails> customerFamilyDetails=customer.getCustomerFamilyDetails();
        System.out.println(customer.getCustomerFamilyDetails());
        System.out.println("Family");
        return ResponseEntity.ok().body(customer);
    }
}
