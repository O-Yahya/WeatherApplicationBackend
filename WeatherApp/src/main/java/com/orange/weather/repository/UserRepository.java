package com.orange.weather.repository;

import com.orange.weather.entity.Role;
import com.orange.weather.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail (String email);
    List<User> findByRole(Role role);
}
