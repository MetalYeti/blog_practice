package ru.yandex.practicum.blog_practice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.blog_practice.entity.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
}
