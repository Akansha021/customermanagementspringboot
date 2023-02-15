package com.example.customermanagement.repository;

import com.example.customermanagement.entity.Customer;
import com.example.customermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    //@Query("from Customer c where c.firstName = '?1'")
    Customer findByFirstName(String firstName);
}
