package ru.yandex.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import ru.yandex.practicum.entity.Post;


public interface PostRepository extends PagingAndSortingRepository<Post, Long>, ListCrudRepository<Post, Long> {
    Page<Post> findByTagsContaining(@NonNull String tags, Pageable pageable);
}
