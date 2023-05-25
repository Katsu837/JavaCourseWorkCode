package com.example.computerAccessoriesStore.repository;

import com.example.computerAccessoriesStore.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface UserRepository extends CrudRepository<User, Long>
{
    User findByEmail(String email);
}
