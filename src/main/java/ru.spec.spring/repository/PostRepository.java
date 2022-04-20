package ru.spec.spring.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.spec.spring.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitleIgnoreCase(String title);

    List<Post> findByDtCreatedBetween(LocalDateTime start, LocalDateTime end);

    List<Post> findByUser_Username(String username);


    //  Написать метод для нахождеия постов, текст(content) которых включает
//  заданную подстроку без учёта регистра букв
//  "world" -> "Hello World", ...
    List<Post> findByContentContainingIgnoreCase(String substring, Sort sort);
}
