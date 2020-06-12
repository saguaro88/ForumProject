package com.javaee.projectFroum.projectForum.services;

import com.javaee.projectFroum.projectForum.exceptions.WrongUsernameException;
import com.javaee.projectFroum.projectForum.models.Post;
import com.javaee.projectFroum.projectForum.models.Topic;
import com.javaee.projectFroum.projectForum.repositories.PostRepository;
import com.javaee.projectFroum.projectForum.repositories.TopicRepository;
import com.javaee.projectFroum.projectForum.services.interfaces.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    TopicRepository topicRepository;

    @Autowired
    PostRepository postRepository;

    @Override
    public Topic getTopicById(long id) {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        return optionalTopic.orElse(null);
    }
    @Override
    public Topic editTopic(Topic topic, long id) throws WrongUsernameException {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userNameToVerfiy = ((UserDetails)principal).getUsername();
        if(userNameToVerfiy.equals(optionalTopic.get().getUser().getUsername())) {
            optionalTopic.get().setTitle(topic.getTitle());
            return topicRepository.save(optionalTopic.get());
        } throw new WrongUsernameException("Logged user isn't post owner.");
    }
    @Override
    public Set<Post> getAllPostsFromTopic(long id) {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        return optionalTopic.get().getPosts();
    }

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @Override
    public Topic addTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public Boolean deleteTopicById(long id) throws WrongUsernameException {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userNameToVerfiy = ((UserDetails)principal).getUsername();
        if(userNameToVerfiy.equals(optionalTopic.get().getUser().getUsername())) {
            if (optionalTopic.isPresent()) {
                topicRepository.deleteById(id);
                return true;
            } return false;
        } throw new WrongUsernameException("Logged user isn't post owner");
    }

    @Override
    public Boolean addPostToTopic(long topicId, Post post) {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        postRepository.save(post);
            if(optionalTopic.isPresent()){
                Topic topic = optionalTopic.get();
                Set<Post> posts = topic.getPosts();
                posts.add(post);
                topic.setPosts(posts);
                topic.getUser().setPostCounter(topic.getUser().getPostCounter()+1);
                topicRepository.save(topic);
                return true;
            }
            return false;
    }
}
