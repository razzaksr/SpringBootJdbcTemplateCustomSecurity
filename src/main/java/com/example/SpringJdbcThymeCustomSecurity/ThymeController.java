package com.example.SpringJdbcThymeCustomSecurity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class ThymeController {
    @GetMapping("/login")
    public String ind(){
        return "index";
    }
    @GetMapping("/dash")
    public String board(){return "dashboard.html";}
}
