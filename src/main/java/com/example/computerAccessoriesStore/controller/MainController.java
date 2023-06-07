package com.example.computerAccessoriesStore.controller;

import com.example.computerAccessoriesStore.entity.Product;
import com.example.computerAccessoriesStore.entity.User;
import com.example.computerAccessoriesStore.repository.ProductRepository;
import com.example.computerAccessoriesStore.repository.UserRepository;
import com.example.computerAccessoriesStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    public ProductService productService;
    @Autowired
    public UserRepository userRepository;

    @GetMapping("/")
    public String main(Model model)
    {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userName);
        String userRole = user.getRole().toString();
        boolean isAdmin = userRole.equals("[ADMIN]");
        model.addAttribute("isADMIN", isAdmin);

        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        model.addAttribute("userName", user.getFullName())
        return "/main";
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestParam String name,
                             @RequestParam int price,
                             @RequestParam("imageFile") MultipartFile imageFile, Model model)
    {
        productService.addProduct(name, price, imageFile);

        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "redirect:/";
    }
    @PostMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable long id)
    {
        productService.deleteProduct(id);
        return "redirect:/";
    }

    @PostMapping("/filter")
    public String filter (@RequestParam String filter, Model model)
    {
        Iterable<Product> products = productRepository.findAll();
        if(filter != null && !filter.isEmpty())
        {
            products = productRepository.findByName(filter);
        }

        model.addAttribute("products",products);
        return "redirect:/";
    }

    @PostMapping("/addProductToCart/{id}")
    public String addProductToCart(@PathVariable long id)
    {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        productService.addProductToCart(id, user);
        return "redirect:/";
    }

    private User getCurrentUser(Principal principal)
    {
        String userName = principal.getName();
        User user = userRepository.findByEmail(userName);
        return user;
    }
}
