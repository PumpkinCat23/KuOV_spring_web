package ru.spec.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spec.spring.entity.User;
import ru.spec.spring.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername (String username) {

        User user = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);
        user.getPosts().size();
        return user;
    }
}
