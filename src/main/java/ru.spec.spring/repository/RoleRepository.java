package ru.spec.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spec.spring.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}