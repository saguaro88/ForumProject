package com.javaee.projectFroum.projectForum.services;

import com.javaee.projectFroum.projectForum.exceptions.WrongUsernameException;
import com.javaee.projectFroum.projectForum.models.Post;
import com.javaee.projectFroum.projectForum.models.Topic;
import com.javaee.projectFroum.projectForum.models.User;
import com.javaee.projectFroum.projectForum.repositories.PostRepository;
import com.javaee.projectFroum.projectForum.repositories.TopicRepository;
import com.javaee.projectFroum.projectForum.services.interfaces.PostService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    TopicRepository topicRepository;

    private boolean isAdmin = false;
    private boolean isUser = false;

    @Override
    public Post getPostById(long id) {
        log.info("Getting post by id.");
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

    @Override
    public List<Post> getAllPosts() {
        log.info("Getting all posts.");
        return postRepository.findAll();
    }

    @Override
    public Post addPost(Post post) {
        log.info("Adding post.");
        post.getUser().setPostCounter(post.getUser().getPostCounter() + 1);
        return postRepository.save(post);
    }

    @Override
    public Boolean deletePostById(long postId, long topicId) throws WrongUsernameException{
        log.info("Deleting post.");
        Optional<Post> optionalPost = postRepository.findById(postId);
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        checkIfAdmin();
        if(checkPostOwner(optionalPost.get().getUser().getUsername()) || isAdmin) {
                if (optionalPost.isPresent()) {
                    if (optionalTopic.isPresent()) {
                        optionalTopic.get().getPosts().remove(optionalPost.get());
                        topicRepository.save(optionalTopic.get());
                        postRepository.deleteById(postId);
                        return true;
                    }
                } log.error("Error while deleting post - post doesn't exist.");
                return false;
            }log.error("Error while deleting post -Logged user isn't post owner. ");
        throw new WrongUsernameException("Logged user isn't post owner.");

    }

    @Override
    public Post editPost(Post post, long topicId, long postId) throws WrongUsernameException {
        log.info("Editing post");
        Optional<Post> optionalPost = postRepository.findById(postId);
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        checkIfAdmin();
        if(checkPostOwner(optionalPost.get().getUser().getUsername()) || isAdmin) {
         if (optionalPost.isPresent()) {
            if (optionalTopic.isPresent()) {
                optionalTopic.get().getPosts().stream().
                        filter(p -> p.getId() == optionalPost.get().getId()).findAny()
                        .get().
                        setContent(post.getContent());
                ;
                topicRepository.save(optionalTopic.get());
                return postRepository.save(optionalPost.get());
            } log.error("Error while editing post, topic doesn't exist.");
        } log.error("Error while editing post, post doesn't exist.");
         return null;
        } log.error("Error while editing post, Logged user isn't post owner.");
        throw new WrongUsernameException("Logged user isn't post owner.");
    }

    private boolean checkPostOwner(String username){
        log.info("Checking post owner.");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userNameToVerfiy = ((UserDetails)principal).getUsername();
            if(userNameToVerfiy.equals(username)) return true;
            else return false;
    }
    private void checkIfAdmin(){
        log.info("Checking if user is admin.");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> authorities
                = ((UserDetails)principal).getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities){
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                isUser = true;
                break;
            }else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }
    }
}