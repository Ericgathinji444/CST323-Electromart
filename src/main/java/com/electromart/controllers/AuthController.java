package com.electromart.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/register")
    public String showRegisterPage() {
        return "auth/register"; // We'll just show the form for now
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "auth/login";
    }
}