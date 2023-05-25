package com.example.computerAccessoriesStore.controller;

import com.example.computerAccessoriesStore.entity.Cart;
import com.example.computerAccessoriesStore.entity.Product;
import com.example.computerAccessoriesStore.entity.User;
import com.example.computerAccessoriesStore.repository.CartRepository;
import com.example.computerAccessoriesStore.repository.ProductRepository;
import com.example.computerAccessoriesStore.repository.UserRepository;
import com.example.computerAccessoriesStore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CartController
{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductService productService;

    @GetMapping("/cart")
    public String cart(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User user = userRepository.findByEmail(userEmail);
        Cart cart = user.getCart();
        if(cart != null)
        {
            Iterable<Product> productsFromCart = cart.getItems();
            model.addAttribute("productsFromCart", productsFromCart);
            model.addAttribute("CartPrice", cart.price);
        }
        else {
            model.addAttribute("nothingInTheCart", true);
        }
        return "/cart";
    }

    @PostMapping("/removeProductFromCart/{id}")
    public String remove(Model model, @PathVariable("id") long id)
    {
        Long productId = id;
        productService.removeProductFromCart(productId);
        return "redirect:/cart";
    }
}


