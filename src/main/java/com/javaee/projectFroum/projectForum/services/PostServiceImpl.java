package com.javaee.projectFroum.projectForum.services;

import com.javaee.projectFroum.projectForum.models.Post;
import com.javaee.projectFroum.projectForum.repositories.PostRepository;
import com.javaee.projectFroum.projectForum.services.interfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;

    @Override
    public Post getPostById(long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
            return optionalPost.orElse(null);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Boolean deletePostById(long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
            if(optionalPost.isPresent()) {
                postRepository.deleteById(id);
                return true;
            }
            return false;
    }
}
