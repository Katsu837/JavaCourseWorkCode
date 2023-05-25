package com.example.computerAccessoriesStore.repository;

import com.example.computerAccessoriesStore.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface ProductRepository extends CrudRepository<Product, Long>
{
    List<Product> findByName(String name);
}
