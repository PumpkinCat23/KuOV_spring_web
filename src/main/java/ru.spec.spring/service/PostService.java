package ru.spec.spring.service;

import ru.spec.spring.dto.PostDto;
import ru.spec.spring.entity.Post;

public interface PostService {
    void create(PostDto postDto);

    Post findById(Long postId);
}
