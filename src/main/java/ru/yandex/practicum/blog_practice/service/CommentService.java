package ru.yandex.practicum.blog_practice.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.blog_practice.entity.Comment;
import ru.yandex.practicum.blog_practice.repository.CommentRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow();
    }

    public void save(Comment comment) {
        commentRepository.save(comment);
    }

}
