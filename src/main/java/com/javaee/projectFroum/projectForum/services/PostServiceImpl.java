package com.javaee.projectFroum.projectForum.services;

import com.javaee.projectFroum.projectForum.exceptions.WrongUsernameException;
import com.javaee.projectFroum.projectForum.models.Post;
import com.javaee.projectFroum.projectForum.models.Topic;
import com.javaee.projectFroum.projectForum.repositories.PostRepository;
import com.javaee.projectFroum.projectForum.repositories.TopicRepository;
import com.javaee.projectFroum.projectForum.services.interfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    TopicRepository topicRepository;

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
        post.getUser().setPostCounter(post.getUser().getPostCounter() + 1);
        return postRepository.save(post);
    }

    @Override
    public Boolean deletePostById(long postId, long topicId) throws WrongUsernameException{
        Optional<Post> optionalPost = postRepository.findById(postId);
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userNameToVerfiy = ((UserDetails)principal).getUsername();
        if(userNameToVerfiy.equals(optionalPost.get().getUser().getUsername())) {
                if (optionalPost.isPresent()) {
                    if (optionalTopic.isPresent()) {
                        optionalTopic.get().getPosts().remove(optionalPost.get());
                        topicRepository.save(optionalTopic.get());
                        postRepository.deleteById(postId);
                        return true;
                    }
                }
                return false;
            } throw new WrongUsernameException("Logged user isn't post owner.");

    }

    @Override
    public Post editPost(Post post, long topicId, long postId) throws WrongUsernameException {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userNameToVerfiy = ((UserDetails)principal).getUsername();
        if(userNameToVerfiy.equals(optionalPost.get().getUser().getUsername())) {
         if (optionalPost.isPresent()) {
            if (optionalTopic.isPresent()) {
                optionalTopic.get().getPosts().stream().
                        filter(p -> p.getId() == optionalPost.get().getId()).findAny()
                        .get().
                        setContent(post.getContent());
                ;
                topicRepository.save(optionalTopic.get());
                return postRepository.save(optionalPost.get());

            }
        }
         return null;
        } throw new WrongUsernameException("Logged user isn't post owner.");
    }
}