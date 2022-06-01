package ru.spec.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.spec.spring.dto.PostDto;
import ru.spec.spring.entity.Post;
import ru.spec.spring.entity.Tag;
import ru.spec.spring.repository.PostRepository;
import ru.spec.spring.repository.TagRepository;
import ru.spec.spring.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static ru.spec.spring.util.SecurityUtils.*;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           UserRepository userRepository,
                           TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }


    @Override
    public void create(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDtCreated(LocalDateTime.now());

        UserDetails details = getCurrentUserDetails();
        post.setUser(userRepository.findByUsername(details.getUsername())
                    .orElseThrow(NoSuchElementException::new));
        post.setTags(parseTags(postDto.getTags()));

        postRepository.save(post);
    }

    @Override
    public Post findById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(NoSuchElementException::new);
        post.getTags().size();
        return post;
    }

    private List<Tag> parseTags(String tags) {
        if (!(StringUtils.hasText(tags))) {
            return Collections.emptyList();
        }

        return Arrays.stream(tags.split(" "))
                            .map(tagName -> tagRepository.findByName(tagName)
                            .orElseGet(() -> tagRepository.save(new Tag(tagName))))
                            .collect(Collectors.toList());

    }
}
