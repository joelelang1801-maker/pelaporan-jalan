package com.pelaporan.pelaporanjalan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pelaporan.pelaporanjalan.model.User;
import com.pelaporan.pelaporanjalan.repository.UserRepository;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // FORM REGISTER
    @GetMapping("/register")
    public String registerForm(Model model) {

        model.addAttribute("user", new User());

        return "register";
    }

    // SIMPAN USER
    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user) {

        user.setRole("ROLE_USER");

        userRepository.save(user);

        return "redirect:/login";
    }

    // HALAMAN LOGIN
   @GetMapping("/login")
public String login() {

    System.out.println("HALAMAN LOGIN DIBUKA");

    return "login";
}
}