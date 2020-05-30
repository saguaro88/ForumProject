package com.javaee.projectFroum.projectForum.services.interfaces;

import com.javaee.projectFroum.projectForum.models.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
}
