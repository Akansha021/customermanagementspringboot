package com.example.customermanagement.controller;

import com.example.customermanagement.Request.JwtRequest;
import com.example.customermanagement.Request.JwtResponse;
import com.example.customermanagement.Request.UserRequest;
import com.example.customermanagement.configurations.JwtUtils;
import com.example.customermanagement.entity.User;
import com.example.customermanagement.repository.UserRepository;
import com.example.customermanagement.responses.ResponseStatus;
import com.example.customermanagement.serviceimpl.UserServiceImpl;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.security.auth.message.callback.PrivateKeyCallback;
import jakarta.security.auth.message.callback.SecretKeyCallback;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.UnknownServiceException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthenticationController {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/user-token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        User user = userRepository.findByUsername(jwtRequest.getUsername());
        Date date= new Date();
        try {
            System.out.println("USER");

            userService.authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
            }

        catch(UsernameNotFoundException e) {
                throw new Exception("User Not Found Exception");
        }
        catch (LockedException e){
            System.out.println(e);

            if (user != null) {

                if (!user.isAccountNonLocked()) {
                    Map<String, String> map = new HashMap<String, String>();
                    String error = "Your account has been locked";
                    map.put("error", error);
                    HttpStatusCode response = HttpStatusCode.valueOf(467);
                    Date lockedDate=user.getDate();
                    long duration  = date.getTime() - lockedDate.getTime();
                    long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
                    long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
                    long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
                    long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);

                    if(lockedDate!=null && diffInSeconds>=30){
                            user.setAccountNonLocked(true);
                            userService.authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
                    }else{
                        return new ResponseEntity<>(error, response);
                    }

                    //throw new Exception("User Locked");

                }else{
                    System.out.println(e.getMessage());
                }

            }else{
                String error = "This user does not exist";

                HttpStatusCode response = HttpStatusCode.valueOf(467);

                return new ResponseEntity<>(error, response);
            }

        }
            UserDetails userToken = this.userService.loadUserByUsername(jwtRequest.getUsername());

            String token = this.jwtUtils.generateToken(userToken);

            return ResponseEntity.ok(new JwtResponse(token));

    }

    @PostMapping("/register")
    public User register(@RequestBody UserRequest userRequest){
        return userService.register(userRequest);
    }

    @GetMapping("/userStatus")
    public String getUserStatus(@RequestParam String username){

        return userService.getUserStatus(username);

    }

}
