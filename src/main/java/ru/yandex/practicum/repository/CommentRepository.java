package ru.yandex.practicum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.entity.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
}
