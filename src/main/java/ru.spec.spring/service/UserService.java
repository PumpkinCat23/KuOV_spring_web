package ru.spec.spring.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.spec.spring.entity.User;

public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    void create(String username, String password);
}
