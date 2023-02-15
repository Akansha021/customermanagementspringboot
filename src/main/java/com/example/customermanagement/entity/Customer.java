package com.example.customermanagement.entity;

import com.example.customermanagement.enums.Gender;
import com.example.customermanagement.enums.MaritalStatus;
import com.example.customermanagement.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "CUSTOMER")
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="CUSTOMER_SEQ")
    @SequenceGenerator(name="CUSTOMER_SEQ", sequenceName="CUSTOMER_SEQ", allocationSize=1)
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
//    @Enumerated(EnumType.STRING)
    private String gender;
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private String status;
    private String citizenshipNumber;
    private String address;
//    @Enumerated(EnumType.STRING)
    private String maritalStatus;
    private String email;
    private String mobileNumber;
    private String addedBy;
    @Temporal(TemporalType.DATE)
    private Date addedDate;
    private String modifiedBy;
    @Temporal(TemporalType.DATE)
    private Date modifiedDate;

    private boolean flag=true;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    List<CustomerFamilyDetails> customerFamilyDetails=new ArrayList<>();

}
