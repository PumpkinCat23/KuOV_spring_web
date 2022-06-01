package ru.spec.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.spec.spring.dto.PostDto;
import ru.spec.spring.entity.Post;
import ru.spec.spring.entity.User;
import ru.spec.spring.repository.PostRepository;
import ru.spec.spring.repository.TagRepository;
import ru.spec.spring.repository.UserRepository;
import ru.spec.spring.service.PostService;
import ru.spec.spring.service.TagService;
import ru.spec.spring.service.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PostController {

    private static final Sort SORT_DT_CREATED = Sort.by("dtCreated").descending();
    private static final Comparator<Post> COM_POST = Comparator.comparing(Post::getDtCreated).reversed();

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final UserService userService;
    private final TagService tagService;
    private final PostService postService;

    @Autowired
    public PostController(PostRepository postRepository,
                          UserRepository userRepository,
                          TagRepository tagRepository,
                          UserService userService,
                          TagService tagService,
                          PostService postService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.userService = userService;
        this.tagService = tagService;
        this.postService = postService;
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

        setCommonParams(model);

        return "blog";

    }

    @GetMapping ("/user/{username}")
    public String byUser (@PathVariable(name = "username") String username, ModelMap model) {

        model.put("title", "Posts by user");
        model.put("subtitle", cropTo20Symbols(username));

//        model.put("posts", postRepository.findByUser_Username(username));
        User user = userService.findByUsername(username);
        List<Post> posts = user.getPosts().stream().sorted(COM_POST).collect(Collectors.toList());
        model.put("posts", posts);

        setCommonParams(model);
        return "blog";
    }

    @GetMapping ("/tag/{tagName}")
    public String byTag(@PathVariable String tagName, ModelMap model) {

        model.put("posts", tagService.findByName(tagName).getPosts()
                .stream().sorted(COM_POST).collect(Collectors.toList()));
        setCommonParams(model);
        return "blog";
    }

    @GetMapping("/post/new")
    public String create (ModelMap model) {
        setCommonParams(model);
        return "post-new";
    }

    @PostMapping("/post/new")
    public String create (PostDto postDto) {
        postService.create(postDto);
        return "redirect:/";
    }

    private void setCommonParams(ModelMap model) {
        model.put("users", userRepository.findAll());
        model.put("tags", tagRepository.findAllSortedByPostCount());
    }

    private String cropTo20Symbols (String str) {
        return  str.length() > 20 ? str.substring(0, 20) + "..." : str;
    }

}