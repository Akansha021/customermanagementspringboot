package com.example.customermanagement.serviceimpl;

import com.example.customermanagement.Request.CustomerFamilyDetailsRequest;
import com.example.customermanagement.Request.CustomerRequest;
import com.example.customermanagement.entity.Customer;
import com.example.customermanagement.entity.CustomerFamilyDetails;
import com.example.customermanagement.repository.CustomerFamilyDetailRepository;
import com.example.customermanagement.services.CustomerFamilyDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FamilyDetailsServiceImpl implements CustomerFamilyDetailService {

    @Autowired
    CustomerFamilyDetailRepository customerFamilyDetailRepository;

    public CustomerFamilyDetails deleteCustomerFamilyDetails(int id){
        CustomerFamilyDetails customerFamilyDetails= customerFamilyDetailRepository.findById(id).orElse(null);
        try {
            customerFamilyDetails.setFlag(false);
            customerFamilyDetailRepository.save(customerFamilyDetails);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return customerFamilyDetails;
    }
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public List<CustomerFamilyDetails> save(List<CustomerFamilyDetailsRequest> customerFamilyDetailsRequest, Customer customer) throws Exception {
        List<CustomerFamilyDetails> customerFamilyDetails=new ArrayList<>();
        List<String>relation= new ArrayList<>();
        int num=0;
        for(CustomerFamilyDetailsRequest customerFamilyDetailReq:customerFamilyDetailsRequest){
            CustomerFamilyDetails customerFamilyDetail=new CustomerFamilyDetails();
            customerFamilyDetail.setRelationship(customerFamilyDetailReq.getRelationship());
            customerFamilyDetail.setRelationPersonName(customerFamilyDetailReq.getRelationPersonName());
            customerFamilyDetail.setCustomer(customer);

            if(!relation.contains(customerFamilyDetail.getRelationship())){
                relation.add(customerFamilyDetail.getRelationship());
            }else{
                throw new Exception("Already Contains this relation");
            }

            if(customerFamilyDetail.getRelationship().equals("Father") || customerFamilyDetail.getRelationship().equals("Mother")
            || customerFamilyDetail.getRelationship().equals("Grand Father") || customerFamilyDetail.getRelationship().equals("Spouse")){
                System.out.println(customerFamilyDetail.getRelationship());
                num+=1;
                System.out.println(num);
            }

            customerFamilyDetails.add(customerFamilyDetail);
        }
        if(customer.getMaritalStatus().equals("Married") && num!=4 || customer.getMaritalStatus().equals("Unmarried") && num!=3){
            throw new Exception("Customer Data is Incomplete");
        }else {
            for (CustomerFamilyDetails cust : customerFamilyDetails) {
                System.out.println(cust.getRelationship());
                System.out.println(cust.getRelationPersonName());
                customerFamilyDetailRepository.save(cust);
            }
        }
        return customerFamilyDetails;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CustomerFamilyDetails> updateCustomerById(List<CustomerFamilyDetailsRequest> customerFamilyDetailsRequest, Customer customer){

        FamilyDetailsServiceImpl familyDetailsService=new FamilyDetailsServiceImpl();
        List<CustomerFamilyDetails> customerFamilyDetails=new ArrayList<>();
        List<Integer> customerFamilyDetailsList=customerFamilyDetailRepository.getCustomerFamilyDetailsId(customer.getId());
        for(int customerFamilyDetailsId: customerFamilyDetailsList) {
            int id=customerFamilyDetailsId;
            for (CustomerFamilyDetailsRequest customerFamilyDetailReq : customerFamilyDetailsRequest) {
                CustomerFamilyDetails customerFamilyDetail = customerFamilyDetailRepository.findById(id).orElse(null);
                customerFamilyDetail.setRelationship(customerFamilyDetailReq.getRelationship());
                customerFamilyDetail.setRelationPersonName(customerFamilyDetailReq.getRelationPersonName());
                customerFamilyDetail.setCustomer(customer);
                customerFamilyDetailRepository.save(customerFamilyDetail);
                customerFamilyDetails.add(customerFamilyDetail);
            }
        }

        for(CustomerFamilyDetails cust:customerFamilyDetails){

            System.out.println(cust.getRelationship());
            System.out.println(cust.getRelationPersonName());
//            customerFamilyDetailRepository.save(cust);

        }
        return customerFamilyDetails;
    }

    public void savecust(List<CustomerFamilyDetailsRequest> customerFamilyDetailsRequest){}

}
