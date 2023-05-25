package com.example.computerAccessoriesStore.service;

import com.example.computerAccessoriesStore.entity.Cart;
import com.example.computerAccessoriesStore.entity.Product;
import com.example.computerAccessoriesStore.entity.User;
import com.example.computerAccessoriesStore.repository.CartRepository;
import com.example.computerAccessoriesStore.repository.ProductRepository;
import com.example.computerAccessoriesStore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public void updateCartPrice(long productId, long userId, boolean IncreaseAmount)
    {
        int price = productRepository.findById(productId).get().getPrice();
        Cart cart = userRepository.findById(userId).get().getCart();
        if(IncreaseAmount) cart.price += price;
        else cart.price -= price;
        cartRepository.save(cart);
    }
}
