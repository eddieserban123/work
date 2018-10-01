package com.example.rest.webservices.restfulwebservices.posts;

import com.example.rest.webservices.restfulwebservices.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserPostController {

    @Autowired
    UserDao usersPost;

    @GetMapping(path = "/users/{userId}/posts")
    public List<Post>  findAllPosts(@PathVariable int userId) {
        return usersPost.findAllPosts(userId);
    }

    @PostMapping(path = "/users/{userId}/posts/")
    public ResponseEntity<Object> savePost(@PathVariable int userId, @RequestBody Post post) {

        Post p= usersPost.addPost(userId, post.getText());
        URI uri =ServletUriComponentsBuilder.fromCurrentRequest().path("{postId}").
                buildAndExpand(p.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }




}
