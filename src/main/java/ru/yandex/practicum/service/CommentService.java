package ru.yandex.practicum.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.entity.Comment;
import ru.yandex.practicum.repository.CommentRepository;

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
