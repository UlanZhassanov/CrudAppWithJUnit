package com.ulanzhasssanov.CrudAppWithJUnit.service;

import com.ulanzhasssanov.CrudAppWithJUnit.model.Post;
import com.ulanzhasssanov.CrudAppWithJUnit.repository.PostRepository;

import java.util.List;

public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post getPostById(Integer id){
        return postRepository.getById(id);
    }

    public List<Post> getAllPosts(){
        return postRepository.getAll();
    }

    public Post savePost(Post post){
        return postRepository.save(post);
    }

    public Post updatePost(Post post){
        return postRepository.update(post);
    }

    public void deletePostById(Integer id){
        postRepository.deleteById(id);
    }
}
