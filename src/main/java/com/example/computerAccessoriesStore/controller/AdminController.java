package com.example.computerAccessoriesStore.controller;

import com.example.computerAccessoriesStore.entity.User;
import com.example.computerAccessoriesStore.repository.UserRepository;
import com.example.computerAccessoriesStore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AdminController
{
    @Autowired
    private UserService service;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/adminPage")
    public String adminPage(Model model){

        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "/adminPage";
    }
    @PostMapping("/adminPage/setUserRole/{id}")
    public String setUserRole(@PathVariable long id)
    {
        service.setUserRole(id);
        return "/adminPage";
    }
    @PostMapping("/adminPage/deleteUser/{id}")
    public String deleteUser(@PathVariable long id)
    {
        service.deleteUser(id);
        return "/adminPage";
    }
}


