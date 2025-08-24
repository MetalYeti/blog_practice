package ru.yandex.practicum.blog_practice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import ru.yandex.practicum.blog_practice.entity.Post;


public interface PostRepository extends PagingAndSortingRepository<Post, Long>, ListCrudRepository<Post, Long> {
    Page<Post> findByTagsContainingOrderById(@NonNull String tags, Pageable pageable);
}
