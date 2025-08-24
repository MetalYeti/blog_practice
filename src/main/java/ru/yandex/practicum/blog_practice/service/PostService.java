package ru.yandex.practicum.blog_practice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.blog_practice.entity.Post;
import ru.yandex.practicum.blog_practice.repository.PostRepository;

import java.util.Optional;

@Service
public class PostService {

    PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<Post> getAllPosts() {
        return getAllPosts(PageRequest.of(0,5));
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Page<Post> getAllPostsContaining(String tags, Pageable pageable) {
        return postRepository.findByTagsContainingOrderById(tags, pageable);
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }
}
