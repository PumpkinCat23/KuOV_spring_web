package ru.spec.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spec.spring.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
