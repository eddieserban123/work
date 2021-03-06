package com.example.rest.webservices.restfulwebservices.posts;


public class Post {

    private Integer id;
    private String text;

    public Post(Integer id, String text) {
        this.id = id;
        this.text = text;
    }

    public Post() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
