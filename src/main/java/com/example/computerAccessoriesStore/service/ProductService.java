package com.example.computerAccessoriesStore.service;

import com.example.computerAccessoriesStore.entity.Cart;
import com.example.computerAccessoriesStore.entity.Product;
import com.example.computerAccessoriesStore.entity.User;
import com.example.computerAccessoriesStore.repository.CartRepository;
import com.example.computerAccessoriesStore.repository.ProductRepository;
import com.example.computerAccessoriesStore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartService cartService;

    @Transactional
    public void addProduct(String name, int price, MultipartFile imageFile)
    {
        String imageSrc = "/Users/maxpodkolzin/CodeOfCursovay/Testing/src/main/resources/static/images/" + name + ".jpg";
        try (OutputStream outputStream = new FileOutputStream(imageSrc)) {
            outputStream.write(imageFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageSrc = "/images/" + name + ".jpg";
        Product product = new Product(name, price, imageSrc);
        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(long id)
    {
        Product product = productRepository.findById(id).get();
        productRepository.delete(product);
    }


    @Transactional
    public void addProductToCart(long productId, String userName)
    {
        long userId = userRepository.findByEmail(userName).getId();
        User user = userRepository.findById(userId).get();
        Cart userCart = user.getCart();
        Product product = productRepository.findById(productId).get();
        if(userCart == null)
        {
            userCart = new Cart();
            userCart.setItems(new ArrayList<Product>());
            userCart.setPrice(0);
            userCart.setCartUser(user);
            user.setCart(userCart);
            cartRepository.save(userCart);
        }

        userCart.getItems().add(product);
        cartService.updateCartPrice(productId, userId, true);
        cartRepository.save(userCart);
        userRepository.save(user);
    }

    @Transactional
    public void removeProductFromCart(long productId)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User user = userRepository.findByEmail(userEmail);
        Cart cart = user.getCart();
        Product product = productRepository.findById(productId).get();
        cart.getItems().remove(product);
        cartService.updateCartPrice(productId, user.getId(), false);
        cartRepository.save(cart);
    }

    public void setImageForProduct(long productId)
    {
        Product product = productRepository.findById(productId).get();
        product.setImageSrc("src/main/resources/static/images/" + product.getName() + ".jpg");
    }
}

