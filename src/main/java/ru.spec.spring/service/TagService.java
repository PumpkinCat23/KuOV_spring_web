package ru.spec.spring.service;

import ru.spec.spring.entity.Tag;

public interface TagService {

    void createTag(String name);

    void createTags(String... names);

    Tag findByName (String tagName);

}
