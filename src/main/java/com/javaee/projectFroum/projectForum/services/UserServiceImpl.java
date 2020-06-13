package com.javaee.projectFroum.projectForum.services;

import com.javaee.projectFroum.projectForum.models.Role;
import com.javaee.projectFroum.projectForum.models.Topic;
import com.javaee.projectFroum.projectForum.models.User;
import com.javaee.projectFroum.projectForum.repositories.RoleRepository;
import com.javaee.projectFroum.projectForum.repositories.UserRepository;
import com.javaee.projectFroum.projectForum.services.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopicServiceImpl topicService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User save(User user) {
        log.info("Creating user.");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roleSet = new HashSet<>();
        Optional<Role> optionalRole = roleRepository.findById(2L);
        roleSet.add(optionalRole.get());
        user.setRoles(roleSet);
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        log.info("Finding user by username.");
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findUserById(long id) {
        log.info("Finding user by ID.");
        return userRepository.findById(id);
    }

    @Override
    public void addTopicToFollowed(long id) {
        log.info("Adding topic to followed.");
        Topic topic = topicService.getTopicById(id);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        User user = findByUsername(username);
        user.getFollowedTopics().add(topic);
        userRepository.save(user);
    }

    @Override
    public void deleteTopicFromFollowed(long id) {
        log.info("Deleting topic from followed.");
        Topic topic = topicService.getTopicById(id);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        User user = findByUsername(username);
        user.getFollowedTopics().remove(topic);
        userRepository.save(user);
    }

    @Override
    public User getCurrentLoggedUser() {
        log.info("Getting current logged user.");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Getting all users.");
        return userRepository.findAll();
    }
}
