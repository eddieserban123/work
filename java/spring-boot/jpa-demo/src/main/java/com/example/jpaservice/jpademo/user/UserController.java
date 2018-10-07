package com.example.jpaservice.jpademo.user;

import com.example.jpaservice.jpademo.post.Post;
import com.example.jpaservice.jpademo.post.PostRepository;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;


    @GetMapping(path = "/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public Resource<User> getUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent() )
            throw new UserNotFoundException("id - " + id);
        Resource<User> resource = new Resource<>(user.get());
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUsers());
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @PostMapping(path = "/users/")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").
                buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/users/{id}/post")
    public List<Post> getAllUsersPost(@PathVariable int id) {

        Optional<User> user =  userRepository.findById(id);
        if (!user.isPresent()) {

            throw new UserNotFoundException(String.format("id - %d", id));
        }
        return user.get().getPosts();
    }

    @PostMapping(path = "/users/{id}/post")
    public ResponseEntity<Object> createUserPost(@PathVariable Integer id, @RequestBody Post post) {
        post.setUser(userRepository.getOne(id));
        Post postSaved= postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{idPost}").
                buildAndExpand(postSaved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }


}
