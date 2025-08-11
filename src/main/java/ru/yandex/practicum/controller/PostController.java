package ru.yandex.practicum.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.entity.Comment;
import ru.yandex.practicum.entity.Post;
import ru.yandex.practicum.model.PageDTO;
import ru.yandex.practicum.service.CommentService;
import ru.yandex.practicum.service.ImageService;
import ru.yandex.practicum.service.PostService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final ImageService imageService;
    private final CommentService commentService;

    public PostController(PostService postService, ImageService imageService, CommentService commentService) {
        this.postService = postService;
        this.imageService = imageService;
        this.commentService = commentService;
    }

    @GetMapping
    public String allPosts(@RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
                           @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize,
                           @RequestParam(name = "search", required = false, defaultValue = "") String search,
                           Model model) {
        Page<Post> posts = postService.getAllPostsContaining(search, PageRequest.of(pageNumber, pageSize));

        model.addAttribute("posts", posts);
        model.addAttribute("paging", new PageDTO<>(posts));
        return "posts";
    }

    @GetMapping("/add")
    public String addPostView() {
        return "add-post";
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addPost(@RequestParam("title") String title,
                          @RequestPart("image") MultipartFile file,
                          @RequestParam("tags") String tags,
                          @RequestParam("text") String text) throws IOException, URISyntaxException {
        Post post = new Post(title,text,tags);
        Post savedPost = postService.save(post);
        imageService.saveImage(String.valueOf(savedPost.getId()), file.getBytes());

        return "redirect:/posts";
    }

    @PostMapping(path = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updatePost(@PathVariable Long id,
                             @RequestParam("tags") String tags,
                             @RequestParam("text") String text,
                             @RequestParam("title") String title,
                             @RequestPart("image") MultipartFile file) throws IOException, URISyntaxException {
        Optional<Post> optional = postService.getPostById(id);
        Post post = optional.orElseThrow();
        post.setCaption(title);
        post.setTags(tags);
        post.setContent(text);
        Post savedPost = postService.save(post);
        imageService.saveImage(String.valueOf(savedPost.getId()), file.getBytes());
        return "redirect:/posts";
    }

    @GetMapping("/{id}")
    public String showPost(@PathVariable Long id, Model model) {
        Optional<Post> post = postService.getPostById(id);
        model.addAttribute("post", post.orElseThrow());
        return "post";
    }

    @PostMapping("/{id}/like")
    public String updateLikes(@PathVariable Long id,
                              @RequestParam("like") boolean like,
                              Model model) {

        Optional<Post> optional = postService.getPostById(id);
        Post post = optional.orElseThrow();
        int likes = post.getLikes();
        if (like) {
            likes++;
        }else{
            likes--;
        }
        post.setLikes(likes);
        post = postService.save(post);
        model.addAttribute("post", post);
        return "redirect:/posts/" + id;
    }

    @GetMapping("/{id}/edit")
    public String editPost(@PathVariable Long id, Model model) {
        Optional<Post> optional = postService.getPostById(id);
        Post post = optional.orElseThrow();
        model.addAttribute("post", post);
        return "add-post";
    }

    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postService.deletePostById(id);
        imageService.deleteImage(id.toString());
        return "redirect:/posts";
    }

    @PostMapping("/{id}/comments")
    public String addComment(@PathVariable Long id, @RequestParam("text") String text, Model model) {
        Optional<Post> optional = postService.getPostById(id);
        Post post = optional.orElseThrow();
        Comment comment = new Comment(text, post.getId());
        post.getComments().add(comment);
        Post savedPost = postService.save(post);
        model.addAttribute("post", savedPost);
        return "post";
    }

    @PostMapping("/{postId}/comments/{commentId}")
    public String editComment(@PathVariable Long postId,
                              @PathVariable Long commentId,
                              @RequestParam("text") String text,
                              Model model) {

        Comment comment = commentService.getCommentById(commentId);
        comment.setComment(text);
        commentService.save(comment);
        Optional<Post> optional = postService.getPostById(postId);
        Post post = optional.orElseThrow();
        model.addAttribute("post", post);
        return "post";
    }

    @PostMapping("/{postId}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable Long postId,
                                @PathVariable Long commentId,
                                Model model) {

        commentService.delete(commentId);
        Optional<Post> optional = postService.getPostById(postId);
        Post post = optional.orElseThrow();
        model.addAttribute("post", post);
        return "post";
    }
}
