package com.example.rest.webservices.restfulwebservices.posts;

import java.util.ArrayList;
import java.util.List;

public class UserPosts {
    private Integer userId;
    private List<Post> userPosts;


    public UserPosts() {
    }

    public UserPosts(int userId, List<Post> userPosts) {
        this.userId = userId;
        this.userPosts = userPosts;
    }

    public UserPosts(int userId) {
        this.userId = userId;
        this.userPosts = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Post> getUserPosts() {
        return userPosts;
    }

    public void setUserPosts(List<Post> userPosts) {
        this.userPosts = userPosts;
    }

    public Post addUserPosts(String text) {
        Post p = new Post(userPosts.size() + 1, text);
        userPosts.add(p);
        return p;
    }
}
