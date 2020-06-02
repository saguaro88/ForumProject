package com.javaee.projectFroum.projectForum.services.interfaces;

import com.javaee.projectFroum.projectForum.models.User;

import java.util.Optional;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
    Optional<User> findUserById(long id);
}
