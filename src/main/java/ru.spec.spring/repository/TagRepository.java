package ru.spec.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.spec.spring.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query(value = "select t.* " +
                    "from tag t " +
                            "inner join post_tag pt " +
                            "on t.tag_id = pt.tag_id " +
                            "group by t.tag_id " +
                            "order by count(*) desc ",
            nativeQuery = true)

    List<Tag> findAllSortedByPostCount();

    Optional<Tag> findByName (String name);


}
