package ru.spec.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spec.spring.repository.TagRepository;
import ru.spec.spring.entity.Tag;

import java.util.Arrays;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional
    public void createTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);

        tagRepository.save(tag);
    }

    @Override
    public void createTags(String... names){
        createTag("");
        Arrays.stream(names).forEach(this::createTag);
    }

}
