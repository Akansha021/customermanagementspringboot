package com.example.customermanagement.Dto;

import com.example.customermanagement.entity.Customer;
import com.example.customermanagement.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private int id;
    private String username;
    private String password;
    private Status status;
    private int attemptCount;

}
