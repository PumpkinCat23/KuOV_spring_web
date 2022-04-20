package ru.spec.spring.service;

import ru.spec.spring.entity.User;

public interface UserService {

    User findByUsername(String username);

}
