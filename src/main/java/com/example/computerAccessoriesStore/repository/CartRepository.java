package com.example.computerAccessoriesStore.repository;

import com.example.computerAccessoriesStore.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface CartRepository extends JpaRepository<Cart, Long>
{

}
