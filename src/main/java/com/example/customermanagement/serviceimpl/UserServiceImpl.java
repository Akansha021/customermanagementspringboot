package com.example.customermanagement.serviceimpl;

import com.example.customermanagement.Dto.UserDto;
import com.example.customermanagement.Request.CustomerRequest;
import com.example.customermanagement.Request.JwtResponse;
import com.example.customermanagement.Request.UserRequest;
import com.example.customermanagement.entity.Customer;
import com.example.customermanagement.entity.User;
import com.example.customermanagement.enums.Status;
import com.example.customermanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    @Lazy
    AuthenticationManager authenticationManager;
    int MAX_COUNT=5;
    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;

    public ResponseEntity<?> authenticate(String username, String password) throws Exception {
        User user=this.userRepository.findByUsername(username);
        try{
            System.out.println("Authenticate 8");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
            user.setAttemptCount(0);
            userRepository.save(user);
        }catch(DisabledException e){
            throw new Exception("USER Disabled"+e.getMessage());
        }catch (BadCredentialsException e){
            this.getPassword(username,password);

            String error = "Your account has been locked";

            System.out.println("Bad Request");

            HttpStatusCode response = HttpStatusCode.valueOf(415);

            throw new ResponseStatusException(response,"Invalid Credential"+e.getMessage());
        }
        System.out.println("count "+user.getAttemptCount());
        return ResponseEntity.ok(user.getAttemptCount());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=new User();
        try {
            user=this.userRepository.findByUsername(username);
            System.out.println("user "+user.getUsername());
            if(!user.isAccountNonLocked()){
                System.out.println("Account locked");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        System.out.println("jj "+user.getUsername());

        if(user==null){
            System.out.println("User Not Found");
            throw  new UsernameNotFoundException("User Not Found");
        }
        return user;
    }

    public User getPassword(String username,String password){
        User user=this.userRepository.findByUsername(username);
        int count =user.getAttemptCount();
        if(count<MAX_COUNT){
            user.setAttemptCount(count+1);
        }else {
            if(user.getDate()==null) {
                user.setAccountNonLocked(false);
                user.setDate(new Date());
            }
            //System.out.println("k");
            System.out.println("You've exceeded the attempt count");
        }

        userRepository.save(user);
        return user;

    }

    public int getAttemptCount(String username){
        User user=this.userRepository.findByUsername(username);

        return user.getAttemptCount();
    }

    public String getUserStatus(String username){
        User user=this.userRepository.findByUsername(username);

        String status=user.getStatus();

        return status;
    }

    public User register(UserRequest userRequest){

        User user=new User();
        //BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(user);

        return user;
    }
}
