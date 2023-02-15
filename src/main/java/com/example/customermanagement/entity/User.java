package com.example.customermanagement.entity;

import com.example.customermanagement.enums.Role;
import com.example.customermanagement.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "USER_LOGIN")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_SEQ")
    @SequenceGenerator(name="USER_SEQ", sequenceName="USER_SEQ", allocationSize=1)
    private int id;
    private String password;

    private String status="Active";
    private String username;
    @Column(name = "attempt_count")
    private int attemptCount=0;
    @Column(name = "account_non_locked")
    private boolean accountNonLocked=true;

    private Date date;

//    @Transient
//    @Enumerated(EnumType.STRING)
//    private Role role;


//    @OneToOne
//    @JoinColumn(name = "CUSTOMER_ID")
//    private Customer customer=new Customer();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Date unLockAccount(){
        return date;
    }

}
