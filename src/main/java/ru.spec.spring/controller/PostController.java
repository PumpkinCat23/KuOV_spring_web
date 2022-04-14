package ru.spec.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.spec.spring.repository.PostRepository;

@Controller
public class PostController {

    private final PostRepository postRepository;

    @Autowired
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @GetMapping ("/blog")
    public String blog(ModelMap model) {
        model.put("posts", postRepository.findAll());

        return "blog";
    }

}