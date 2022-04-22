package ru.spec.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spec.spring.entity.Role;
import ru.spec.spring.entity.User;
import ru.spec.spring.repository.RoleRepository;
import ru.spec.spring.repository.UserRepository;

import javax.persistence.EntityExistsException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder,
                           UserRepository userRepository,
                           RoleRepository roleRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findByUsername (String username) {

        User user = userRepository.findByUsername(username).
                    orElseThrow(NoSuchElementException::new);
        user.getPosts().size();
        return user;
    }

    @Override
    public void create(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new EntityExistsException(String.format
                    ("Username '%s' already exists", username));
        };

        User user = new User();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setActive(true);
        user.setDtCreated(LocalDateTime.now());
        user.setRoles(Arrays.asList(roleRepository.findByName(Role.USER)
                      .orElseThrow(NoSuchElementException::new)));

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username)
                                        throws UsernameNotFoundException {
        User user = findByUsername(username);
        user.getRoles().size();
        return user;
    }
}
