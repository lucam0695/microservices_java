package com.spring_sec.spring_jwt.util.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring_sec.spring_jwt.model.Post;
import com.spring_sec.spring_jwt.model.Profile;
import com.spring_sec.spring_jwt.model.UserInstabasic;
import com.spring_sec.spring_jwt.repository.PostRepository;


@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Post update(Long id, Post post) {
        Optional<Post> postResult = postRepository.findById(id);

        if (postResult.isPresent()) {
            Post postUpdate = postResult.get();
            postUpdate.setDescription(post.getDescription());
            postUpdate.setTitle(post.getTitle());
            postUpdate.setUrl(post.getUrl());
            postRepository.save(postUpdate);
            return postUpdate;
        } else {
            throw new Error("Post has no uptated");
        }

    }

    public List<Post> findByProfile(Profile profile){
        return postRepository.findByProfile(profile);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}