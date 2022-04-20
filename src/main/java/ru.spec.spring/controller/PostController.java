package ru.spec.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.spec.spring.entity.User;
import ru.spec.spring.repository.PostRepository;
import ru.spec.spring.repository.UserRepository;
import ru.spec.spring.service.UserService;

import java.util.NoSuchElementException;

@Controller
public class PostController {

    private static final Sort SORT_DT_CREATED = Sort.by("dtCreated").descending();
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public PostController(PostRepository postRepository,
                          UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


    @GetMapping ("/")
    public String blog(@RequestParam(name = "q", required = false) String query,
                       ModelMap model) {

        if (StringUtils.hasText(query)) {
            model.put("title", "Search by ");
            model.put("subtitle", cropTo20Symbols(query));
            model.put("posts", postRepository.findByContentContainingIgnoreCase(query, SORT_DT_CREATED));
        } else {
            model.put("title", "All posts");
            model.put("posts", postRepository.findAll(SORT_DT_CREATED));
        }

        model.put("users", userRepository.findAll());

        return "blog";

    }

    @GetMapping ("/user/{username}")
    public String byUser (@PathVariable(name = "username") String username, ModelMap model) {

        model.put("title", "Posts by user");
        model.put("subtitle", cropTo20Symbols(username));

//        model.put("posts", postRepository.findByUser_Username(username));
        User user = userService.findByUsername(username);
        model.put("posts", user.getPosts());

        return "blog";
    }

    private String cropTo20Symbols (String str) {
        return  str.length() > 20 ? str.substring(0, 20) + "..." : str;
    }

}