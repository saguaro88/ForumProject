package com.javaee.projectFroum.projectForum.services;

import com.javaee.projectFroum.projectForum.exceptions.WrongUsernameException;
import com.javaee.projectFroum.projectForum.models.Post;
import com.javaee.projectFroum.projectForum.models.Topic;
import com.javaee.projectFroum.projectForum.models.User;
import com.javaee.projectFroum.projectForum.repositories.PostRepository;
import com.javaee.projectFroum.projectForum.repositories.TopicRepository;
import com.javaee.projectFroum.projectForum.services.interfaces.TopicService;
import com.javaee.projectFroum.projectForum.services.interfaces.UserService;
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
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        return optionalTopic.orElse(null);
    }
    @Override
    public Topic editTopic(Topic topic, long id) throws WrongUsernameException {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        checkIfAdmin();
        if(checkPostOwner(optionalTopic.get().getUser().getUsername()) || isAdmin) {
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
        checkIfAdmin();
        if(checkPostOwner(optionalTopic.get().getUser().getUsername()) || isAdmin){
            if (optionalTopic.isPresent()) {
                topicRepository.deleteById(id);
                return true;
            } return false;
        } throw new WrongUsernameException("Logged user isn't topic owner");
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
                return true;
            }
            return false;
    }
    private boolean checkPostOwner(String username){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userNameToVerfiy = ((UserDetails)principal).getUsername();
        if(userNameToVerfiy.equals(username)) return true;
        else return false;
    }
    private void checkIfAdmin(){
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
