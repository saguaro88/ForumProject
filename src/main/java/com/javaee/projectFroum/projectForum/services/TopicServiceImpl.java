package com.javaee.projectFroum.projectForum.services;

import com.javaee.projectFroum.projectForum.exceptions.WrongUsernameException;
import com.javaee.projectFroum.projectForum.models.Post;
import com.javaee.projectFroum.projectForum.models.Topic;
import com.javaee.projectFroum.projectForum.models.User;
import com.javaee.projectFroum.projectForum.repositories.PostRepository;
import com.javaee.projectFroum.projectForum.repositories.TopicRepository;
import com.javaee.projectFroum.projectForum.services.interfaces.TopicService;
import com.javaee.projectFroum.projectForum.services.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService {
    @Autowired
    TopicRepository topicRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    MailService mailService;

    @Autowired
    PostRepository postRepository;

    private boolean isAdmin = false;
    private boolean isUser = false;

    @Override
    public Topic getTopicById(long id) {
        log.info("Getting topic by ID.");
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        return optionalTopic.orElse(null);
    }
    @Override
    public Topic editTopic(Topic topic, long id) throws WrongUsernameException {
        log.info("Edditing topic.");
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        checkIfAdmin();
        if(checkPostOwner(optionalTopic.get().getUser().getUsername()) || isAdmin) {
            optionalTopic.get().setTitle(topic.getTitle());
            log.info("Topic edited.");
            return topicRepository.save(optionalTopic.get());
        }log.error("Error while editing topic, logged user isn't post owner.");
        throw new WrongUsernameException("Logged user isn't post owner.");
    }
    @Override
    public Set<Post> getAllPostsFromTopic(long id) {
        log.info("Getting all posts from topic.");
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        return optionalTopic.get().getPosts();
    }

    @Override
    public List<Topic> getAllTopics() {
        log.info("Getting all topics.");
        return topicRepository.findAll();
    }

    @Override
    public Topic addTopic(Topic topic) {
        log.info("Adding topic.");
        return topicRepository.save(topic);
    }

    @Override
    public Boolean deleteTopicById(long id) throws WrongUsernameException {
        log.info("Deleting topic.");
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        checkIfAdmin();
        if(checkPostOwner(optionalTopic.get().getUser().getUsername()) || isAdmin){
            if (optionalTopic.isPresent()) {
                topicRepository.deleteById(id);
                log.info("Topic deleted.");
                return true;
            } log.error("Error while deleting topic.");
            return false;
        }log.error("Error while deleting topic, logged user isn't topic owner.");
        throw new WrongUsernameException("Logged user isn't topic owner");
    }

    @Override
    public Boolean addPostToTopic(long topicId, Post post) {
        log.info("Adding post to topic.");
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        postRepository.save(post);
            if(optionalTopic.isPresent()){
                Topic topic = optionalTopic.get();
                Set<Post> posts = topic.getPosts();
                posts.add(post);
                topic.setPosts(posts);
                topic.getUser().setPostCounter(topic.getUser().getPostCounter()+1);
                topicRepository.save(topic);
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                for(User user : userService.getAllUsers()){
                    for(Topic t : user.getFollowedTopics()){
                        if(t.getId() == topicId){
                            mailMessage.setTo(user.getEmail());
                            mailMessage.setSubject("New post in followed topic.");
                            mailMessage.setFrom("${spring.mail.username}");
                            mailMessage.setText("There is a new post in topic you're following. \n" +
                                    "Topic: " + optionalTopic.get().getTitle()+ "\n" +
                                    "Post: " + post.getContent());
                            mailService.sendEmail(mailMessage);
                        }
                    }
                }
                log.info("Post added to topic.");
                return true;
            }
            log.error("Error while adding post to topic.");
            return false;
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
