package controller;

import entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/list")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping("/add")
    public boolean addPost(@RequestBody Post post) {
        return postService.addPost(post);
    }

    @PostMapping("/edit")
    public boolean editPost(@RequestBody Post post) {
        return postService.updatePost(post);
    }

    @DeleteMapping("/delete/{postId}")
    public boolean deletePost(@PathVariable Integer postId) {
        return postService.deletePost(postId);
    }
}