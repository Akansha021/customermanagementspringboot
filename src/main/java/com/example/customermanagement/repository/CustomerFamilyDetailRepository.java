package com.example.customermanagement.repository;

import com.example.customermanagement.entity.CustomerFamilyDetails;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerFamilyDetailRepository extends JpaRepository<CustomerFamilyDetails,Integer> {
    @Query("select C.id from CustomerFamilyDetails C INNER join Customer Cu on C.customer =?1")
    List<Integer> getCustomerFamilyDetailsId(int id);
}
