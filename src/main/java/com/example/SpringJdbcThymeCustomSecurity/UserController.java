package com.example.SpringJdbcThymeCustomSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService service;
    @Autowired
    PasswordEncoder passwordEncoder;
    @PostMapping("/signup")
    public String signup(@RequestBody HaiUsers haiUsers){
        haiUsers.setPassword(passwordEncoder.encode(haiUsers.getPassword()));
        return service.insertOne(haiUsers);
    }
}
