package com.example.customermanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CUSTOMER_FAMILY_DETAILS")
public class CustomerFamilyDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="CUSTOMERFAMILY_SEQ")
    @SequenceGenerator(name="CUSTOMERFAMILY_SEQ", sequenceName="CUSTOMERFAMILY_SEQ", allocationSize=1)
    private int id;
    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    @JsonIgnore
    private Customer customer=new Customer();
    private boolean flag=true;
    private String relationship;
    private String relationPersonName;

}
