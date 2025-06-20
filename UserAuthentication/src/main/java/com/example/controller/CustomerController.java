package com.example.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @PostMapping("/register")
    public void register(@RequestBody Map<String, String> credentials, HttpServletResponse response) throws IOException {
        String username = credentials.get("username");
        String password = credentials.get("password");
        // TODO: Add registration logic here
//        return "Registration successful for customer: " + username;
        response.sendRedirect("/customer/login");
    }

    @GetMapping("/login")
    public String loginPage() {
        // This method returns the name of the login view
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        // TODO: Add login logic here
        return "Login successful for customer: " + username;
    }

    @GetMapping("/state")
    public Integer state(){
        OpenTelemetry openTelemetry = GlobalOpenTelemetry.get();
        Tracer tracer = openTelemetry.getTracer("instrumentation-library-name", "1.0.0");
        return 200;
    }
}

