package com.example.computerAccessoriesStore.controller;

import com.example.computerAccessoriesStore.entity.User;
import com.example.computerAccessoriesStore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){return "/login";}

    @GetMapping("/registration")
    public String registrationPage(){return "/registration";}

    @PostMapping("/registration")
    public String addUser (User user, Model model)
    {
        if(userService.addUserToDataBase(user)) return "/login";
        model.addAttribute("errorMassage", "User already registered");
        return "redirect:/registration";
    }

}



