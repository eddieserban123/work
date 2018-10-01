package com.example.rest.webservices.restfulwebservices.user;

import com.example.rest.webservices.restfulwebservices.posts.Post;
import com.example.rest.webservices.restfulwebservices.posts.UserPosts;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDao {
    private static List<User> users = new ArrayList<>();

    private static List<UserPosts> userPosts = new ArrayList<>();

    static {
        users.add(new User(1, "Peter", new Date()));
        userPosts.add(new UserPosts(1));
        users.add(new User(2, "Paul", new Date()));
        userPosts.add(new UserPosts(2));
        users.add(new User(3, "Marry", new Date()));
        userPosts.add(new UserPosts(3));

    }

    public List<User> findAll() {
        return users;
    }

    public User find(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    public User save(User user) {
        User createdUser = new User(users.size() + 1, user.getName(), user.getBirthday());
        users.add(createdUser);
        return createdUser;
    }

    public List<Post> findAllPosts(int userId) {
        return userPosts.stream().
                filter(up -> up.getUserId() == userId).findFirst().
                map(UserPosts::getUserPosts).orElse(null);
    }

    public Post findPostIdforUserId(int userId, int postId) {
        return userPosts.stream().
                filter(up -> up.getUserId() == userId).findFirst().
                map(UserPosts::getUserPosts).orElse(new ArrayList<>()).
                stream().filter(p -> p.getId() == postId).findFirst().orElse(null);
    }

    public Post addPost(int userId, String text) {
        UserPosts userPost = userPosts.stream().filter(up -> up.getUserId() == userId).findFirst().orElse(null);
        if (userPost != null) {
            return userPost.addUserPosts(text);
        }
        return null;
    }

}
