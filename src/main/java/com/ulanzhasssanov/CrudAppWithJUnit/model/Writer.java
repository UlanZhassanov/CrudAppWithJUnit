package com.ulanzhasssanov.CrudAppWithJUnit.model;

import com.ulanzhasssanov.CrudAppWithJUnit.enums.Status;

import java.util.List;


public class Writer {
    private int id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    private Status status;

    public Writer() {
    }

    public Writer(int id, String firstName, String lastName, List<Post> posts, Status status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
        this.status = status;
    }

    public Writer(String firstName, String lastName, Status status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Writer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", posts='" + posts + '\'' +
                ", status=" + status +
                '}';
    }
}
